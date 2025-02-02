package app.sgsc.domain.dto.response;

import app.sgsc.domain.db.rds.entity.CollegeDepartment;
import com.querydsl.core.annotations.QueryProjection;
import java.io.Serializable;

/**
 * @param collegeDepartmentId 대학 학과 ID
 * @param collegeDepartmentName 대학 학과 이름
 * @param collegeDepartmentNumber 대학 학과 번호
 */
public record CollegeDepartmentResultDto(
    Long collegeDepartmentId,
    String collegeDepartmentName,
    String collegeDepartmentNumber
) implements Serializable {

    @QueryProjection
    public CollegeDepartmentResultDto {
        // ...
    }

    @QueryProjection
    public CollegeDepartmentResultDto(CollegeDepartment collegeDepartment) {
        this(collegeDepartment.getId(), collegeDepartment.getName(), collegeDepartment.getNumber());
    }
}