package app.sgsc.domain.dto.response;

import java.io.Serializable;

public record CourseRegistrationQueueOrderResultDto(
    Long userId,
    Long courseId
) implements Serializable {
    // ...
}