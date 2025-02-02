package app.sgsc.domain.dto.response;

import app.sgsc.domain.db.rds.entity.College;
import com.querydsl.core.annotations.QueryProjection;
import java.io.Serializable;

/**
 * @param collegeId 대학 ID
 * @param collegeName 대학 이름
 * @param collegeNumber 대학 번호
 */
public record CollegeResultDto(
    Long collegeId,
    String collegeName,
    String collegeNumber
) implements Serializable {

    @QueryProjection
    public CollegeResultDto {
        // ...
    }

    @QueryProjection
    public CollegeResultDto(College college) {
        this(college.getId(), college.getName(), college.getNumber());
    }
}