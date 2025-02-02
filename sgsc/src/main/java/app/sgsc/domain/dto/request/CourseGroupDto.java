package app.sgsc.domain.dto.request;

import java.io.Serializable;

/**
 * @param courseGroupName 강의 그룹 이름
 * @param courseGroupNumber 강의 그룹 번호
 */
public record CourseGroupDto(
    String courseGroupName,
    String courseGroupNumber
) implements Serializable {
    // ...
}