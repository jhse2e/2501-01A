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
 * 강의
 */
@Getter
@Entity
@Table(
    name = "course",
    indexes = {
        // @Index(name = "course_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // 강의 번호는 고유해야 한다.
        @UniqueConstraint(name = "course_uk_1", columnNames = {"number"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends AbstractEntity implements Serializable {

    /**
     * 강의 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 강의 구분
     */
    @Column(nullable = false)
    private String type;

    /**
     * 강의 번호
     */
    @Column(nullable = false)
    private String number;

    /**
     * 강의 시간표
     */
    @Column(nullable = false)
    private String timetable;

    /**
     * 강의 개설 연도
     */
    @Column(nullable = false)
    private String year;

    /**
     * 강의 개설 학기
     */
    @Column(nullable = false)
    private String semester;

    /**
     * 강의 학점
     */
    @Column(nullable = false)
    private Integer credit;

    /**
     * 수강 신청 인원
     */
    @Column(nullable = false)
    private Integer registrationCount;

    /**
     * 예비 수강 신청 인원
     */
    @Column(nullable = false)
    private Integer registrationCountCart;

    /**
     * 수강 신청 잔여 인원
     */
    @Column(nullable = false)
    private Integer registrationCountLeft;

    /**
     * 수강 신청 제한 인원
     */
    @Column(nullable = false)
    private Integer registrationCountLimit;

    /**
     * 강의 그룹
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CourseGroup courseGroup;

    @Builder(access = AccessLevel.PUBLIC)
    public Course(Long id, String type, String number, String timetable, String year, String semester, Integer credit, Integer registrationCount, Integer registrationCountCart, Integer registrationCountLeft, Integer registrationCountLimit, CourseGroup courseGroup) {
        this.id = id;
        this.type = type;
        this.number = number;
        this.timetable = timetable;
        this.year = year;
        this.semester = semester;
        this.credit = credit;
        this.registrationCount = registrationCount;
        this.registrationCountCart = registrationCountCart;
        this.registrationCountLeft = registrationCountLeft;
        this.registrationCountLimit = registrationCountLimit;
        this.courseGroup = courseGroup;
    }

    public static Course create(String type, String number, String timetable, String year, String semester, Integer credit, Integer registrationCount, Integer registrationCountCart, Integer registrationCountLeft, Integer registrationCountLimit, CourseGroup courseGroup) {
        return Course.builder()
                .type(type)
                .number(number)
                .timetable(timetable)
                .year(year)
                .semester(semester)
                .credit(credit)
                .registrationCount(registrationCount)
                .registrationCountCart(registrationCountCart)
                .registrationCountLeft(registrationCountLeft)
                .registrationCountLimit(registrationCountLimit)
                .courseGroup(courseGroup)
                .build();
    }

    public Boolean isRegistrable() {
        // 수강 신청 잔여 인원이 부족한 경우, 수강 신청할 수 없다.
        if (this.registrationCountLeft == 0) return false;

        return true;
    }

    public void updateByRegistrationCountPlus() {
        // 수강 신청 인원이 수강 신청 제한 인원을 초과한 경우
        if (this.registrationCount > this.registrationCountLimit) {
            throw ApiException.of400(ApiExceptionType.COURSE_REGISTRATION_COUNT_NOT_VALID);
        }

        this.registrationCount = this.registrationCount + 1;
        this.registrationCountLeft = this.registrationCountLeft - 1;
    }

    public void updateByRegistrationCountMinus() {
        // 수강 신청 인원이 없는 경우
        if (this.registrationCount == 0) {
            throw ApiException.of400(ApiExceptionType.COURSE_REGISTRATION_COUNT_NOT_VALID);
        }

        this.registrationCount = this.registrationCount - 1;
        this.registrationCountLeft = this.registrationCountLeft + 1;
    }

    public void updateByRegistrationCountCartPlus() {
        this.registrationCountCart = this.registrationCountCart + 1;
    }

    public void updateByRegistrationCountCartMinus() {
        // 예비 수강 신청 인원이 없는 경우
        if (this.registrationCountCart == 0) {
            throw ApiException.of400(ApiExceptionType.COURSE_REGISTRATION_CART_COUNT_NOT_VALID);
        }

        this.registrationCountCart = this.registrationCountCart - 1;
    }
}