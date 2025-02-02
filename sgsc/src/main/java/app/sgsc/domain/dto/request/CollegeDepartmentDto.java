package app.sgsc.domain.dto.request;

import java.io.Serializable;

/**
 * @param collegeDepartmentName 대학 학과 이름
 * @param collegeDepartmentNumber 대학 학과 번호
 */
public record CollegeDepartmentDto(
    String collegeDepartmentName,
    String collegeDepartmentNumber
) implements Serializable {
    // ...
}