package app.sgsc.domain.dto.response;

import app.sgsc.domain.db.rds.entity.CollegeDivision;
import com.querydsl.core.annotations.QueryProjection;
import java.io.Serializable;

/**
 * @param collegeDivisionId 대학 학부 ID
 * @param collegeDivisionName 대학 학부 이름
 * @param collegeDivisionNumber 대학 학부 번호
 */
public record CollegeDivisionResultDto(
    Long collegeDivisionId,
    String collegeDivisionName,
    String collegeDivisionNumber
) implements Serializable {

    @QueryProjection
    public CollegeDivisionResultDto {
        // ...
    }

    @QueryProjection
    public CollegeDivisionResultDto(CollegeDivision collegeDivision) {
        this(collegeDivision.getId(), collegeDivision.getName(), collegeDivision.getNumber());
    }
}