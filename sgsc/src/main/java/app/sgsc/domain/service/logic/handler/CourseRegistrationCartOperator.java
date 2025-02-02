package app.sgsc.domain.service.logic.handler;

import app.sgsc.domain.db.rds.entity.Course;
import app.sgsc.domain.db.rds.entity.CourseRegistrationCart;
import app.sgsc.domain.db.rds.entity.User;
import app.sgsc.domain.db.rds.repository.query.CourseRegistrationCartRepository;
import app.sgsc.domain.db.rds.repository.query.CourseRepository;
import app.sgsc.domain.db.rds.repository.query.UserRepository;
import app.sgsc.global.configuration.exception.ApiException;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRegistrationCartOperator {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseRegistrationCartRepository courseRegistrationCartRepository;

    /**
     * 예비 수강 신청 처리한다.
     */
    @Transactional
    public CourseRegistrationCart create(Long userId, Long courseId) {
        // 1. 사용자, 강의를 조회한다.
        User user = userRepository.getUserByUserId(userId).orElseThrow(() -> ApiException.of404(ApiExceptionType.USER_NOT_EXISTED));
        Course course = courseRepository.getCourseByCourseId(courseId).orElseThrow(() -> ApiException.of404(ApiExceptionType.COURSE_NOT_EXISTED));
        // 2. 예비 수강 신청을 생성한다.
        CourseRegistrationCart courseRegistrationCart = courseRegistrationCartRepository.save(CourseRegistrationCart.create(user, course));
        courseRegistrationCart.updateByCreate();

        return courseRegistrationCart;
    }

    /**
     * 예비 수강 신청 취소 처리한다.
     */
    @Transactional
    public void delete(Long userId, Long courseId) {
        // 1. 예비 수강 신청을 조회한다.
        CourseRegistrationCart courseRegistrationCart = courseRegistrationCartRepository.getCourseRegistrationCartByUserIdAndCourseId(userId, courseId).orElseThrow(() -> ApiException.of404(ApiExceptionType.COURSE_REGISTRATION_CART_NOT_EXISTED));
        courseRegistrationCart.updateByDelete();
        // 2. 예비 수강 신청을 삭제한다.
        courseRegistrationCartRepository.delete(courseRegistrationCart);
    }
}