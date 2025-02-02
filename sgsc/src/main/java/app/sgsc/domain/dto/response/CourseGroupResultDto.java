package app.sgsc.domain.dto.response;

import app.sgsc.domain.db.rds.entity.CourseGroup;
import com.querydsl.core.annotations.QueryProjection;
import java.io.Serializable;

/**
 * @param id
 * @param name
 * @param number
 */
public record CourseGroupResultDto(
    Long id,
    String name,
    String number
) implements Serializable {

    @QueryProjection
    public CourseGroupResultDto {
        // ...
    }

    @QueryProjection
    public CourseGroupResultDto(CourseGroup courseGroup) {
        this(courseGroup.getId(), courseGroup.getName(), courseGroup.getName());
    }
}