package app.sgsc.domain.dto.response;

import app.sgsc.domain.db.rds.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import java.io.Serializable;

/**
 * @param userId 사용자 ID
 * @param userName 사용자 이름
 * @param userNumber 사용자 번호
 * @param userRegistrationCredit 사용자 신청 학점
 * @param userRegistrationCreditLeft 사용자 잔여 신청 학점
 * @param userRegistrationCreditLimit 사용자 신청 제한 학점
 */
public record UserResultDto(
    Long userId,
    String userName,
    String userNumber,
    Integer userRegistrationCredit,
    Integer userRegistrationCreditLeft,
    Integer userRegistrationCreditLimit
) implements Serializable {

    @QueryProjection
    public UserResultDto {
        // ...
    }

    @QueryProjection
    public UserResultDto(User user) {
        this(user.getId(), user.getName(), user.getNumber(), user.getRegistrationCredit(), user.getRegistrationCreditLeft(), user.getRegistrationCreditLimit());
    }
}