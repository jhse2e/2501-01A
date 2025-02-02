package app.sgsc.domain.dto.response;

import java.io.Serializable;

public record CourseRegistrationQueueResultDto(
    Long userId,
    Long courseId,
    String message
) implements Serializable {
    // ...
}