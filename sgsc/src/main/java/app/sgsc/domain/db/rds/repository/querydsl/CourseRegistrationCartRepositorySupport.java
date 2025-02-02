package app.sgsc.domain.db.rds.repository.querydsl;

import app.sgsc.domain.db.rds.entity.CourseRegistrationCart;
import app.sgsc.domain.dto.response.CourseRegistrationCartResultDto;
import java.util.List;
import java.util.Optional;

public interface CourseRegistrationCartRepositorySupport {

    /**
     * 예비 수강 신청 중복을 확인한다.
     */
    Boolean hasCourseRegistrationCartByUserIdAndCourseId(
        Long userId,
        Long courseId
    );

    /**
     * 예비 수강 신청을 조회한다.
     */
    Optional<CourseRegistrationCart> getCourseRegistrationCartByUserIdAndCourseId(
        Long userId,
        Long courseId
    );

    /**
     * 예비 수강 신청 목록을 조회한다.
     */
    List<CourseRegistrationCartResultDto> getCourseRegistrationCartResultDtosByUserId(
        Long userId
    );
}