package app.sgsc.domain.dto.request;

import java.io.Serializable;

/**
 * @param collegeName 대학 이름
 * @param collegeNumber 대학 번호
 */
public record CollegeDto(
    String collegeName,
    String collegeNumber
) implements Serializable {
    // ...
}