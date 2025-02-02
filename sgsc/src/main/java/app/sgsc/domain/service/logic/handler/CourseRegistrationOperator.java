package app.sgsc.domain.service.logic.handler;

import app.sgsc.domain.db.rds.entity.Course;
import app.sgsc.domain.db.rds.entity.CourseRegistration;
import app.sgsc.domain.db.rds.entity.User;
import app.sgsc.domain.db.rds.repository.query.CourseRegistrationRepository;
import app.sgsc.domain.db.rds.repository.query.CourseRepository;
import app.sgsc.domain.db.rds.repository.query.UserRepository;
import app.sgsc.global.configuration.exception.ApiException;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRegistrationOperator {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    /**
     * 수강 신청 처리한다.
     */
    @Transactional
    public CourseRegistration create(Long userId, Long courseId) {
        // 1. 사용자, 강의를 조회한다.
        User user = userRepository.getUserByUserId(userId).orElseThrow(() -> ApiException.of404(ApiExceptionType.USER_NOT_EXISTED));
        Course course = courseRepository.getCourseByCourseId(courseId, LockModeType.PESSIMISTIC_WRITE).orElseThrow(() -> ApiException.of404(ApiExceptionType.COURSE_NOT_EXISTED));
        // 2. 수강 신청을 생성한다.
        CourseRegistration courseRegistration = courseRegistrationRepository.save(CourseRegistration.create(user, course, course.getCourseGroup()));
        courseRegistration.updateByCreate();

        return courseRegistration;
    }

    /**
     * 수강 신청 취소 처리한다.
     */
    @Transactional
    public void delete(Long userId, Long courseId) {
        // 1. 수강 신청을 조회한다.
        CourseRegistration courseRegistration = courseRegistrationRepository.getCourseRegistrationByUserIdAndCourseId(userId, courseId).orElseThrow(() -> ApiException.of404(ApiExceptionType.COURSE_REGISTRATION_NOT_EXISTED));
        courseRegistration.updateByDelete();
        // 2. 수강 신청을 삭제한다.
        courseRegistrationRepository.delete(courseRegistration);
    }
}