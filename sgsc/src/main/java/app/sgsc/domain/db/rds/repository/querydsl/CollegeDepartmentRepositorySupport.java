package app.sgsc.domain.db.rds.repository.querydsl;

import app.sgsc.domain.db.rds.entity.CollegeDepartment;
import app.sgsc.domain.dto.response.CollegeDepartmentResultDto;
import java.util.List;
import java.util.Optional;

public interface CollegeDepartmentRepositorySupport {

    /**
     * 대학 학과를 조회한다.
     */
    Optional<CollegeDepartment> getCollegeDepartmentByCollegeDepartmentNameAndCollegeDepartmentNumber(
        String collegeDepartmentName,
        String collegeDepartmentNumber
    );

    /**
     * 대학 학과 목록을 조회한다.
     */
    List<CollegeDepartmentResultDto> getCollegeDepartmentResultDtosByCollegeId(
        Long collegeId
    );

    /**
     * 대학 학과 목록을 조회한다.
     */
    List<CollegeDepartmentResultDto> getCollegeDepartmentResultDtosByCollegeId(
        Long collegeId,
        Long collegeDivisionId
    );
}