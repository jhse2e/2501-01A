package app.sgsc.domain.dto.request;

import java.io.Serializable;

/**
 * @param userName 사용자 이름
 * @param userNumber 사용자 번호
 * @param userPassword 사용자 비밀번호
 * @param userRegistrationCredit 사용자 신청 학점
 * @param userRegistrationCreditLeft 사용자 잔여 신청 학점
 * @param userRegistrationCreditLimit 사용자 신청 제한 학점
 */
public record UserDto(
    String userName,
    String userNumber,
    String userPassword,
    Integer userRegistrationCredit,
    Integer userRegistrationCreditLeft,
    Integer userRegistrationCreditLimit
) implements Serializable {
    // ...
}