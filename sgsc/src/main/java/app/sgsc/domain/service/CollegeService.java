package app.sgsc.domain.service;

import app.sgsc.domain.dto.response.CollegeDepartmentResultDto;
import app.sgsc.domain.dto.response.CollegeDivisionResultDto;
import app.sgsc.domain.dto.response.CollegeResultDto;
import java.util.List;

public interface CollegeService {

    /**
     * 대학 목록을 조회한다.
     */
    List<CollegeResultDto> getColleges(
        // ...
    );

    /**
     * 대학 학부 목록을 조회한다.
     */
    List<CollegeDivisionResultDto> getCollegeDivisions(
        Long collegeId
    );

    /**
     * 대학 학과 목록을 조회한다.
     */
    List<CollegeDepartmentResultDto> getCollegeDepartments(
        Long collegeId
    );

    /**
     * 대학 학과 목록을 조회한다.
     */
    List<CollegeDepartmentResultDto> getCollegeDepartments(
        Long collegeId,
        Long collegeDivisionId
    );
}