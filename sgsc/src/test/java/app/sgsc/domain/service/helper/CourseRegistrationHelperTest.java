package app.sgsc.domain.service.helper;

import app.sgsc.domain.db.rds.entity.Course;
import app.sgsc.domain.db.rds.entity.CourseGroup;
import app.sgsc.domain.db.rds.entity.CourseRegistration;
import app.sgsc.domain.db.rds.entity.User;
import app.sgsc.domain.db.rds.repository.query.CourseRegistrationRepository;
import app.sgsc.domain.db.rds.repository.query.CourseRepository;
import app.sgsc.domain.db.rds.repository.query.UserRepository;
import app.sgsc.domain.service.logic.helper.CourseRegistrationHelper;
import app.sgsc.global.configuration.exception.ApiException;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

/**
 * No matching tests found in any candidate test task.
 * 설정 -> 빌드, 실행, 배포 -> 빌드 도구 -> Gradle -> 다음을 사용하여 테스트 실행 (IntelliJ 선택)
 */
@ExtendWith(value = MockitoExtension.class)
public class CourseRegistrationHelperTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseRegistrationRepository courseRegistrationRepository;

    @InjectMocks
    private CourseRegistrationHelper courseRegistrationHelper;

    @BeforeEach
    public void setup() {
        // ...
    }

    @Test
    @DisplayName(value = "수강 신청 유효성 테스트 1")
    public void checkTest1() {
        // 수강 신청하는 사용자
        User user = new User(1L, "학생1", "20240001", "", 15, 0, 15);
        // 수강 신청하는 강의 그룹 및 강의
        CourseGroup courseGroup = new CourseGroup(1L, null, null, null, null, null);
        Course course = new Course(1L, "전공", "1001", "월 09:00~10:30, 수 09:00~10:30", "2024", "1", 3, 0, 0, 5, 5, courseGroup);

        // Given (위에서 설정한 데이터가 조회되도록 지정한다.)
        given(userRepository.getUserByUserId(1L)).willReturn(Optional.of(user));
        given(courseRepository.getCourseByCourseId(1L)).willReturn(Optional.of(course));

        // When, Then (사용자 학점이 잘못된 경우)
        assertThrows(ApiException.class, () -> {
            courseRegistrationHelper.check(1L, 1L);
        }, ApiExceptionType.COURSE_REGISTRATION_CREDIT_NOT_VALID.getMessage());
    }

    @Test
    @DisplayName(value = "수강 신청 유효성 테스트 2")
    public void checkTest2() {
        // 수강 신청하는 사용자
        User user = new User(1L, "학생1", "20240001", "", 0, 15, 15);
        // 수강 신청하는 강의 그룹 및 강의
        CourseGroup courseGroup = new CourseGroup(1L, null, null, null, null, null);
        Course course = new Course(1L, "전공", "1001", "월 09:00~10:30, 수 09:00~10:30", "2024", "1", 3, 5, 0, 0, 5, courseGroup);

        // Given (위에서 설정한 데이터가 조회되도록 지정한다.)
        given(userRepository.getUserByUserId(1L)).willReturn(Optional.of(user));
        given(courseRepository.getCourseByCourseId(1L)).willReturn(Optional.of(course));

        // When, Then (수강 신청 인원이 잘못된 경우)
        assertThrows(ApiException.class, () -> {
            courseRegistrationHelper.check(1L, 1L);
        }, ApiExceptionType.COURSE_REGISTRATION_COUNT_NOT_VALID.getMessage());
    }

    @Test
    @DisplayName(value = "수강 신청 유효성 테스트 3")
    public void checkTest3() {
        // ...
    }

    @Test
    @DisplayName(value = "수강 신청 유효성 테스트 4")
    public void checkTest4() {
        // ...
    }

    @Test
    @DisplayName(value = "수강 신청 유효성 테스트 5")
    public void checkTest5() {
        // 수강 신청하는 사용자
        User user = new User(1L, "학생1", "20240001", "", 3, 12, 15);
        // 수강 신청하는 강의 그룹 및 강의
        CourseGroup courseGroup = new CourseGroup(2L, null, null, null, null, null);
        Course course = new Course(2L, "전공", "1002", "월 13:00~14:30, 수 13:00~14:30", "2024", "1", 3, 0, 0, 5, 5, courseGroup);
        // 사용자의 기등록된 수강 신청 목록
        List<CourseRegistration> courseRegistrations = new ArrayList<>(){{
            {
                CourseGroup courseGroup = new CourseGroup(1L, null, null, null, null, null);
                Course course = new Course(1L, "전공", "1001", "월 13:00~14:30, 수 13:00~14:30", "2024", "1", 3, 0, 0, 5, 5, courseGroup);
                add(new CourseRegistration(1L, user, course, courseGroup));
            }
        }};

        // Given (위에서 설정한 데이터가 조회되도록 지정한다.)
        given(userRepository.getUserByUserId(1L)).willReturn(Optional.of(user));
        given(courseRepository.getCourseByCourseId(2L)).willReturn(Optional.of(course));
        given(courseRegistrationRepository.getCourseRegistrationsByUserId(1L)).willReturn(courseRegistrations);

        // When, Then (강의 시간이 중복된 경우)
        assertThrows(ApiException.class, () -> {
            courseRegistrationHelper.check(1L, 2L);
        }, ApiExceptionType.COURSE_REGISTRATION_COURSE_TIMETABLE_DUPLICATED.getMessage());
    }

    @Test
    @DisplayName(value = "수강 신청 유효성 테스트 6")
    public void checkTest6() {
        // 수강 신청하는 사용자
        User user = new User(1L, "학생1", "20240001", "", 3, 12, 15);
        // 수강 신청하는 강의 그룹 및 강의
        CourseGroup courseGroup = new CourseGroup(2L, null, null, null, null, null);
        Course course = new Course(2L, "전공", "1002", "월 12:00~13:30, 수 12:00~13:30", "2024", "1", 3, 0, 0, 5, 5, courseGroup);
        // 사용자의 기등록된 수강 신청 목록
        List<CourseRegistration> courseRegistrations = new ArrayList<>(){{
            {
                CourseGroup courseGroup = new CourseGroup(1L, null, null, null, null, null);
                Course course = new Course(1L, "전공", "1001", "월 13:00~14:30, 수 13:00~14:30", "2024", "1", 3, 0, 0, 5, 5, courseGroup);
                add(new CourseRegistration(1L, user, course, courseGroup));
            }
        }};

        // Given (위에서 설정한 데이터가 조회되도록 지정한다.)
        given(userRepository.getUserByUserId(1L)).willReturn(Optional.of(user));
        given(courseRepository.getCourseByCourseId(2L)).willReturn(Optional.of(course));
        given(courseRegistrationRepository.getCourseRegistrationsByUserId(1L)).willReturn(courseRegistrations);

        // When, Then (강의 시간이 중복된 경우)
        assertThrows(ApiException.class, () -> {
            courseRegistrationHelper.check(1L, 2L);
        }, ApiExceptionType.COURSE_REGISTRATION_COURSE_TIMETABLE_DUPLICATED.getMessage());
    }

    @Test
    @DisplayName(value = "수강 신청 유효성 테스트 7")
    public void checkTest7() {
        // 수강 신청하는 사용자
        User user = new User(1L, "학생1", "20240001", "", 3, 12, 15);
        // 수강 신청하는 강의 그룹 및 강의
        CourseGroup courseGroup = new CourseGroup(2L, null, null, null, null, null);
        Course course = new Course(2L, "전공", "1002", "월 13:30~15:00, 수 13:30~15:00", "2024", "1", 3, 0, 0, 5, 5, courseGroup);
        // 사용자의 기등록된 수강 신청 목록
        List<CourseRegistration> courseRegistrations = new ArrayList<>(){{
            {
                CourseGroup courseGroup = new CourseGroup(1L, null, null, null, null, null);
                Course course = new Course(1L, "전공", "1001", "월 13:00~14:30, 수 13:00~14:30", "2024", "1", 3, 0, 0, 5, 5, courseGroup);
                add(new CourseRegistration(1L, user, course, courseGroup));
            }
        }};

        // Given (위에서 설정한 데이터가 조회되도록 지정한다.)
        given(userRepository.getUserByUserId(1L)).willReturn(Optional.of(user));
        given(courseRepository.getCourseByCourseId(2L)).willReturn(Optional.of(course));
        given(courseRegistrationRepository.getCourseRegistrationsByUserId(1L)).willReturn(courseRegistrations);

        // When, Then (강의 시간이 중복된 경우)
        assertThrows(ApiException.class, () -> {
            courseRegistrationHelper.check(1L, 2L);
        }, ApiExceptionType.COURSE_REGISTRATION_COURSE_TIMETABLE_DUPLICATED.getMessage());
    }
}