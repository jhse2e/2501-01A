package app.sgsc.domain.db.rds.repository.querydsl;

import app.sgsc.domain.db.rds.entity.CollegeDivision;
import app.sgsc.domain.dto.response.CollegeDivisionResultDto;
import java.util.List;
import java.util.Optional;

public interface CollegeDivisionRepositorySupport {

    /**
     * 대학 학부를 조회한다.
     */
    Optional<CollegeDivision> getCollegeDivisionByCollegeDivisionNameAndCollegeDivisionNumber(
        String collegeDivisionName,
        String collegeDivisionNumber
    );

    /**
     * 대학 학부 목록을 조회한다.
     */
    List<CollegeDivisionResultDto> getCollegeDivisionResultDtosByCollegeId(
        Long collegeId
    );
}