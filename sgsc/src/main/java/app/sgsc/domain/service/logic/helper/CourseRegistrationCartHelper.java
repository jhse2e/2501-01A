package app.sgsc.domain.service.logic.helper;

import app.sgsc.domain.db.rds.repository.query.CourseRegistrationCartRepository;
import app.sgsc.global.configuration.exception.ApiException;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRegistrationCartHelper {
    private final CourseRegistrationCartRepository courseRegistrationCartRepository;

    /**
     * 사용자가 강의를 예비 수강 신청할 수 있는지 확인한다.
     */
    @Transactional(readOnly = true)
    public void check(Long userId, Long courseId) {
        if (courseRegistrationCartRepository.hasCourseRegistrationCartByUserIdAndCourseId(userId, courseId)) {
            throw ApiException.of409(ApiExceptionType.COURSE_REGISTRATION_CART_COURSE_DUPLICATED);
        }
    }
}