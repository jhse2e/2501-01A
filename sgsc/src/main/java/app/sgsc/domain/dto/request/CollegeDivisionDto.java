package app.sgsc.domain.dto.request;

import java.io.Serializable;

/**
 * @param collegeDivisionName 대학 학부 이름
 * @param collegeDivisionNumber 대학 학부 번호
 */
public record CollegeDivisionDto(
    String collegeDivisionName,
    String collegeDivisionNumber
) implements Serializable {
    // ...
}