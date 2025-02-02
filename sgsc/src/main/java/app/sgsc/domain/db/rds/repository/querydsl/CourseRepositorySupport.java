package app.sgsc.domain.db.rds.repository.querydsl;

import app.sgsc.domain.db.rds.entity.Course;
import app.sgsc.domain.dto.response.CourseResultDto;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface CourseRepositorySupport {

    /**
     * 강의를 조회한다.
     */
    Optional<Course> getCourseByCourseId(
        Long courseId
    );

    /**
     * 강의를 조회한다.
     */
    Optional<Course> getCourseByCourseId(
        Long courseId,
        LockModeType lockModeType
    );

    /**
     * 강의를 조회한다.
     */
    Optional<Course> getCourseByCourseNumber(
            String courseNumber
    );

    /**
     * 수강 신청 인원이 있는 강의 목록을 조회한다.
     * (여러 명의 사용자가 동일한 강의를 수강 신청한 경우는 제외한다.)
     */
    List<Course> getCoursesBy(
        // ...
    );

    /**
     * 강의 목록을 조회한다.
     */
    List<CourseResultDto> getCourseResultDtosBy(
        Long collegeId,
        Long collegeDivisionId,
        Long collegeDepartmentId,
        String courseType,
        String courseYear,
        String courseSemester
    );
}