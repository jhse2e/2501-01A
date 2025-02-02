package app.sgsc.domain.db.rds.repository.querydsl;

import app.sgsc.domain.db.rds.entity.College;
import app.sgsc.domain.dto.response.CollegeResultDto;
import java.util.List;
import java.util.Optional;

public interface CollegeRepositorySupport {

    /**
     * 대학을 조회한다.
     */
    Optional<College> getCollegeByCollegeNameAndCollegeNumber(
        String collegeName,
        String collegeNumber
    );

    /**
     * 대학 목록을 조회한다.
     */
    List<CollegeResultDto> getCollegeDtosBy(
        // ...
    );
}