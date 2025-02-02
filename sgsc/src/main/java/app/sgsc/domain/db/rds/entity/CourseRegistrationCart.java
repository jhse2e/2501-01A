package app.sgsc.domain.db.rds.entity;

import app.sgsc.global.common.db.rds.entity.AbstractLogEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 예비 수강 신청
 */
@Getter
@Entity
@Table(
    name = "course_registration_cart",
    indexes = {
        // @Index(name = "course_registration_cart_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // 사용자 ID + 강의 ID는 고유해야 한다. (중복 강의를 신청할 수 없다.)
        @UniqueConstraint(name = "course_registration_cart_uk_1", columnNames = {"user_id", "course_id"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseRegistrationCart extends AbstractLogEntity implements Serializable {

    /**
     * 예비 수강 신청 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    /**
     * 강의
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Course course;

    @Builder(access = AccessLevel.PUBLIC)
    public CourseRegistrationCart(Long id, User user, Course course) {
        this.id = id;
        this.user = user;
        this.course = course;
    }

    public static CourseRegistrationCart create(User user, Course course) {
        return CourseRegistrationCart.builder()
                .user(user)
                .course(course)
                .build();
    }

    public void updateByCreate() {
        this.course.updateByRegistrationCountCartPlus();
    }

    public void updateByDelete() {
        this.course.updateByRegistrationCountCartMinus();
    }
}