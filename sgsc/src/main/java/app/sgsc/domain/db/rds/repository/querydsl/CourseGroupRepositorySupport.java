package app.sgsc.domain.db.rds.repository.querydsl;

import app.sgsc.domain.db.rds.entity.CourseGroup;
import app.sgsc.domain.dto.response.CourseGroupResultDto;
import java.util.List;
import java.util.Optional;

public interface CourseGroupRepositorySupport {

    /**
     * 대학 강의를 조회한다.
     */
    Optional<CourseGroup> getCourseGroupByCourseGroupNameAndCourseGroupName(
        String courseGroupName,
        String courseGroupNumber
    );

    /**
     * 대학 강의 목록을 조회한다.
     */
    List<CourseGroupResultDto> getCourseGroupResultDtosBy(
        Long collegeId,
        Long collegeDivisionId,
        Long collegeDepartmentId
    );
}