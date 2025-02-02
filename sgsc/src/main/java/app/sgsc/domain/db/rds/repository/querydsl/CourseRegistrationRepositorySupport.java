package app.sgsc.domain.db.rds.repository.querydsl;

import app.sgsc.domain.db.rds.entity.CourseRegistration;
import app.sgsc.domain.dto.response.CourseRegistrationResultDto;
import java.util.List;
import java.util.Optional;

public interface CourseRegistrationRepositorySupport {

    /**
     * 강의를 기준으로 수강 신청 중복을 확인한다.
     */
    Boolean hasByUserIdAndCourseId(
        Long userId,
        Long courseId
    );

    /**
     * 강의 그룹을 기준으로 수강 신청 중복을 확인한다.
     */
    Boolean hasByUserIdAndCourseGroupId(
        Long userId,
        Long courseGroupId
    );

    /**
     * 강의 ID와 일치하는 수강 신청 개수를 조회한다.
     */
    Integer getCourseRegistrationCountByCourseId(
        Long courseId
    );

    /**
     * 수강 신청을 조회한다.
     */
    Optional<CourseRegistration> getCourseRegistrationByUserIdAndCourseId(
        Long userId,
        Long courseId
    );

    /**
     * 수강 신청 목록을 조회한다.
     */
    List<CourseRegistration> getCourseRegistrationsByUserId(
        Long userId
    );

    /**
     * 수강 신청 목록을 조회한다.
     */
    List<CourseRegistrationResultDto> getCourseRegistrationDtosByUserId(
        Long userId
    );
}