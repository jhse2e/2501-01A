package app.sgsc.domain.dto.request;

import java.io.Serializable;

/**
 * @param userId 사용자 ID
 * @param courseId 강의 ID
 */
public record CourseRegistrationQueueDto(
    Long userId,
    Long courseId
) implements Serializable {
    // ...
}