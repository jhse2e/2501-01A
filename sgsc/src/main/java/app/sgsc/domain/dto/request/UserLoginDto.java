package app.sgsc.domain.dto.request;

import java.io.Serializable;

/**
 * @param userNumber 사용자 번호
 * @param userPassword 사용자 비밀번호
 */
public record UserLoginDto(
    String userNumber,
    String userPassword
) implements Serializable {
    // ...
}