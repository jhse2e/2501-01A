package app.sgsc.domain.enumeration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CourseRegistrationStatusType {
    COMPLETED("COMPLETED"),
    CLOSED("CLOSED"),
    FAILED("FAILED");

    private final String value;
}