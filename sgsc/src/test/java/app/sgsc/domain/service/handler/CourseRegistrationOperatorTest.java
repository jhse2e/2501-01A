package app.sgsc.domain.service.handler;

import app.sgsc.domain.db.rds.entity.Course;
import app.sgsc.domain.db.rds.entity.CourseRegistration;
import app.sgsc.domain.db.rds.entity.User;
import app.sgsc.domain.db.rds.repository.query.CourseRegistrationRepository;
import app.sgsc.domain.db.rds.repository.query.CourseRepository;
import app.sgsc.domain.db.rds.repository.query.UserRepository;
import app.sgsc.domain.service.logic.handler.CourseRegistrationOperator;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * No matching tests found in any candidate test task.
 * 설정 -> 빌드, 실행, 배포 -> 빌드 도구 -> Gradle -> 다음을 사용하여 테스트 실행 (IntelliJ 선택)
 */
@ExtendWith(value = MockitoExtension.class)
public class CourseRegistrationOperatorTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseRegistrationRepository courseRegistrationRepository;

    @InjectMocks
    private CourseRegistrationOperator courseRegistrationOperator;

    @BeforeEach
    public void setup() {
        // ...
    }

    @Test
    @DisplayName(value = "수강 신청 처리 테스트")
    public void createTest() {
        Long userId = 1L;
        Long courseId = 1L;
        User user = new User(userId, "학생1", "20240001", "", 0, 15, 15);
        Course course = new Course(courseId, "전공", "1001", "월 09:00~10:30, 수 09:00~10:30", "2024", "1", 3, 0, 0, 5, 5, null);
        CourseRegistration courseRegistration = new CourseRegistration(1L, user, course, null);

        // Given
        given(userRepository.getUserByUserId(userId)).willReturn(Optional.of(user));
        given(courseRepository.getCourseByCourseId(courseId, LockModeType.PESSIMISTIC_WRITE)).willReturn(Optional.of(course));
        given(courseRegistrationRepository.save(any())).willReturn(courseRegistration);

        // When
        courseRegistrationOperator.create(userId, courseId);

        // Then (수강 신청 인원이 1임을 확인한다.)
        assertThat(courseRegistration.getCourse().getRegistrationCount()).isEqualTo(1);
        // Then (신청 학점이 3임을 확인한다.)
        assertThat(courseRegistration.getUser().getRegistrationCredit()).isEqualTo(3);
        // Then (잔여 신청 학점이 12임을 확인한다.)
        assertThat(courseRegistration.getUser().getRegistrationCreditLeft()).isEqualTo(12);
    }

    @Test
    @DisplayName(value = "수강 신청 취소 처리 테스트")
    public void deleteTest() {
        Long userId = 1L;
        Long courseId = 1L;
        User user = new User(userId, "학생1", "20240001", "", 3, 12, 15);
        Course course = new Course(courseId, "전공", "1001", "월 09:00~10:30, 수 09:00~10:30", "2024", "1", 3, 1, 0, 5, 5, null);
        CourseRegistration courseRegistration = new CourseRegistration(1L, user, course, null);

        // Given
        given(courseRegistrationRepository.getCourseRegistrationByUserIdAndCourseId(userId, courseId)).willReturn(Optional.of(courseRegistration));

        // When
        courseRegistrationOperator.delete(userId, courseId);

        // Then (수강 신청 인원이 0임을 확인한다.)
        assertThat(courseRegistration.getCourse().getRegistrationCount()).isEqualTo(0);
        // Then (신청 학점이 0임을 확인한다.)
        assertThat(courseRegistration.getUser().getRegistrationCredit()).isEqualTo(0);
        // Then (잔여 신청 학점이 15임을 확인한다.)
        assertThat(courseRegistration.getUser().getRegistrationCreditLeft()).isEqualTo(15);
    }
}