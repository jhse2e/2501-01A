package app.sgsc.domain.db.rds.entity;

import app.sgsc.global.common.db.rds.entity.AbstractEntity;
import app.sgsc.global.configuration.exception.ApiException;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 사용자
 */
@Getter
@Entity
@Table(
    name = "user",
    indexes = {
        // @Index(name = "user_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // 사용자 번호(학번)는 고유해야 한다.
        @UniqueConstraint(name = "user_uk_1", columnNames = {"number"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractEntity implements Serializable {

    /**
     * 사용자 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자 이름
     */
    @Column(nullable = false)
    private String name;

    /**
     * 사용자 번호(학번)
     */
    @Column(nullable = false)
    private String number;

    /**
     * 사용자 비밀번호
     */
    @Column(nullable = false)
    private String password;

    /**
     * 사용자 신청 학점
     */
    @Column(nullable = false)
    private Integer registrationCredit;

    /**
     * 사용자 신청 잔여 학점
     */
    @Column(nullable = false)
    private Integer registrationCreditLeft;

    /**
     * 사용자 신청 제한 학점
     */
    @Column(nullable = false)
    private Integer registrationCreditLimit;

    @Builder(access = AccessLevel.PUBLIC)
    public User(Long id, String name, String number, String password, Integer registrationCredit, Integer registrationCreditLeft, Integer registrationCreditLimit) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.password = password;
        this.registrationCredit = registrationCredit;
        this.registrationCreditLeft = registrationCreditLeft;
        this.registrationCreditLimit = registrationCreditLimit;
    }

    public static User create(String name, String number, String password, Integer registrationCredit, Integer registrationCreditLeft, Integer registrationCreditLimit) {
        return User.builder()
                .name(name)
                .number(number)
                .password(password)
                .registrationCredit(registrationCredit)
                .registrationCreditLeft(registrationCreditLeft)
                .registrationCreditLimit(registrationCreditLimit)
                .build();
    }

    public Boolean isRegistrable(int credit) {
        // 잔여 신청 학점이 부족한 경우, 수강 신청할 수 없다.
        if (this.registrationCreditLeft < credit) return false;
        // 신청 제한 학점을 초과한 경우, 수강 신청할 수 없다.
        if (this.registrationCreditLimit < this.registrationCredit + credit) return false;

        return true;
    }

    /**
     * 수강 신청 시, 학점을 계산한다.
     */
    public void updateByRegistrationCreditPlus(int credit) {
        // 잔여 신청 학점이 부족한 경우, 수강 신청할 수 없다.
        if (this.registrationCreditLeft < credit) {
            throw ApiException.of400(ApiExceptionType.COURSE_REGISTRATION_CREDIT_LEFT_NOT_VALID);
        }

        int creditCalculated = this.registrationCredit + credit; // 신청 학점 증가
        int creditLeftCalculated = this.registrationCreditLeft - credit; // 신청 잔여 학점 감소

        // 신청 제한 학점을 초과한 경우, 수강 신청할 수 없다.
        if (this.registrationCreditLimit < creditCalculated) {
            throw ApiException.of400(ApiExceptionType.COURSE_REGISTRATION_CREDIT_LIMIT_NOT_VALID);
        }

        this.registrationCredit = creditCalculated;
        this.registrationCreditLeft = creditLeftCalculated;
    }

    /**
     * 수강 신청 취소 시, 학점을 계산한다.
     */
    public void updateByRegistrationCreditMinus(int credit) {
        // 신청 학점이 0인 경우, 수강 신청을 취소할 수 없다.
        if (this.registrationCredit == 0) {
            throw ApiException.of400(ApiExceptionType.COURSE_REGISTRATION_CREDIT_NOT_VALID);
        }

        int creditCalculated = this.registrationCredit - credit; // 신청 학점 감소
        int creditLeftCalculated = this.registrationCreditLeft + credit; // 신청 잔여 학점 증가

        this.registrationCredit = creditCalculated;
        this.registrationCreditLeft = creditLeftCalculated;
    }
}