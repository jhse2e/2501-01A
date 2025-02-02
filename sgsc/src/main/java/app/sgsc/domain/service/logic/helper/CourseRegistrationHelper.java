package app.sgsc.domain.service.logic.helper;

import app.sgsc.domain.db.rds.entity.Course;
import app.sgsc.domain.db.rds.entity.CourseRegistration;
import app.sgsc.domain.db.rds.entity.User;
import app.sgsc.domain.db.rds.repository.query.CourseRegistrationRepository;
import app.sgsc.domain.db.rds.repository.query.CourseRepository;
import app.sgsc.domain.db.rds.repository.query.UserRepository;
import app.sgsc.global.configuration.exception.ApiException;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRegistrationHelper {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    /**
     * 사용자가 강의를 수강 신청할 수 있는지 확인한다.
     */
    @Transactional(readOnly = true)
    public void check(Long userId, Long courseId) {
        User user = userRepository.getUserByUserId(userId).orElseThrow(() -> ApiException.of404(ApiExceptionType.USER_NOT_EXISTED));
        Course course = courseRepository.getCourseByCourseId(courseId).orElseThrow(() -> ApiException.of404(ApiExceptionType.COURSE_NOT_EXISTED));
        List<CourseRegistration> courseRegistrations = courseRegistrationRepository.getCourseRegistrationsByUserId(userId);

        // 사용자 학점을 확인한다.
        if (!user.isRegistrable(course.getCredit())) {
            throw ApiException.of400(ApiExceptionType.COURSE_REGISTRATION_CREDIT_NOT_VALID);
        }

        // 수강 신청 인원을 확인한다.
        if (!course.isRegistrable()) {
            throw ApiException.of400(ApiExceptionType.COURSE_REGISTRATION_COUNT_NOT_VALID);
        }

        // 수강 신청 중복을 확인한다. (Course.id = 1을 2번 요청하는 경우)
        if (courseRegistrationRepository.hasByUserIdAndCourseId(userId, courseId)) {
            throw ApiException.of409(ApiExceptionType.COURSE_REGISTRATION_COURSE_DUPLICATED);
        }

        // 강의 그룹 중복을 확인한다. (Course.id = 1, Course.id = 2를 요청하는 경우 (id = 1, id = 2는 동일한 강의 그룹))
        if (courseRegistrationRepository.hasByUserIdAndCourseGroupId(userId, course.getCourseGroup().getId())) {
            throw ApiException.of409(ApiExceptionType.COURSE_REGISTRATION_COURSE_GROUP_DUPLICATED);
        }

        // 강의 시간 중복을 확인한다. (Course.id = 1, Course.id = 17을 요청하는 경우)
        if (isDuplicatedCourseTimetable(course, courseRegistrations)) {
            throw ApiException.of409(ApiExceptionType.COURSE_REGISTRATION_COURSE_TIMETABLE_DUPLICATED);
        }
    }

    /**
     * 강의 시간 중복을 확인한다.
     */
    private Boolean isDuplicatedCourseTimetable(Course course, List<CourseRegistration> courseRegistrations) {
        // 사용자의 수강 신청 목록을 기반으로 요일별 강의 시간표 맵을 생성한다.
        Map<String, List<Duration>> timetableMap = new HashMap<>();

        // 요일별 강의 시간표를 초기화한다.
        for (String day : new String[]{"월", "화", "수", "목", "금"}) {
            timetableMap.put(day, new ArrayList<>());
        }

        // 사용자의 수강 신청 목록을 요일별 강의 시간표에 추가한다.
        for (String timetables : courseRegistrations.stream().map(e -> e.getCourse().getTimetable()).toList()) {
            for (String timetable : timetables.split(", ")) { // ex) "월 09:00~10:15", "수 09:00~10:15"
                String day = timetable.substring(0, 1); // ex) "월", "수"
                String dayTime = timetable.substring(2); // ex) "09:00~10:15", "09:00~10:15"

                timetableMap.get(day).add(timeToDuration(dayTime));
            }
        }

        // 요일별 강의 시간표에서 새롭게 신청하는 강의의 강의 시간 중복을 확인한다.
        for (String timetable : course.getTimetable().split(", ")) { // ex) "월 09:00~10:15", "수 09:00~10:15"
            String day = timetable.substring(0, 1); // ex) "월", "수"
            String dayTime = timetable.substring(2); // ex) "09:00~10:15", "09:00~10:15"

            for (Duration duration : timetableMap.get(day)) {
                if (compareDuration(duration, timeToDuration(dayTime))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 강의 시간을 숫자로 환산한다.
     */
    private Duration timeToDuration(String time) {
        String from = time.split("~")[0]; // ex) "09:00"
        String to = time.split("~")[1]; // ex) "10:15"
        Integer fromDigit = (Integer.parseInt(from.split(":")[0]) * 60) + Integer.parseInt(from.split(":")[1]); // ex) 540
        Integer toDigit = (Integer.parseInt(to.split(":")[0]) * 60) + Integer.parseInt(to.split(":")[1]); // ex) 615

        return new Duration(fromDigit, toDigit);
    }

    /**
     * 강의 시간을 비교한다.
     */
    private Boolean compareDuration(Duration o1, Duration o2) {
        if (o1.from().equals(o2.from())) {
            System.out.println("1번 문제");
            return true; // 1번
        } else {
            // ______[ o1 ]______
            // ______[ o2 ]______ 1번 (o1, o2의 시작 시간이 겹치기 때문에 중복됨)
            // ___[ o2 ]_________ 2번 (o1의 시작 시간이 o2의 종료 시간과 겹치기 때문에 중복됨)
            // _________[ o2 ]___ 3번 (o1의 종료 시간이 o2의 시작 시간과 겹치기 때문에 중복됨)
            // [ o2 ]____________ 4번 (o1의 시작 시간이 o2의 종료 시간과 겹치지 않기 때문에 중복되지 않음)
            // ____________[ o2 ] 5번 (o1의 종료 시간이 o2의 시작 시간과 겹치지 않기 때문에 중복되지 않음)
            if (o1.from() > o2.from() && o1.from() < o2.to()) {
                System.out.println("2번 문제");
                return true; // 2번
            }
            if (o1.from() < o2.from() && o1.to() > o2.from()) {
                System.out.println("3번 문제");
                return true; // 3번
            }
        }

        return false;
    }

    private record Duration(Integer from, Integer to) {}
}