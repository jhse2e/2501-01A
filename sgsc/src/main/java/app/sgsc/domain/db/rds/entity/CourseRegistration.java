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
 * 수강 신청
 */
@Getter
@Entity
@Table(
    name = "course_registration",
    indexes = {
        // @Index(name = "course_registration_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // 사용자 ID + 강의 ID는 고유해야 한다. (중복 강의를 신청할 수 없다.)
        @UniqueConstraint(name = "course_registration_uk_1", columnNames = {"user_id", "course_id"}),
        // 사용자 ID + 강의 그룹 ID는 고유해야 한다. (중복 강의 그룹을 신청할 수 없다.)
        @UniqueConstraint(name = "course_registration_uk_2", columnNames = {"user_id", "course_group_id"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseRegistration extends AbstractLogEntity implements Serializable {

    /**
     * 수강 신청 ID
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

    /**
     * 강의 그룹
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CourseGroup courseGroup;

    @Builder(access = AccessLevel.PUBLIC)
    public CourseRegistration(Long id, User user, Course course, CourseGroup courseGroup) {
        this.id = id;
        this.user = user;
        this.course = course;
        this.courseGroup = courseGroup;
    }

    public static CourseRegistration create(User user, Course course, CourseGroup courseGroup) {
        return CourseRegistration.builder()
                .user(user)
                .course(course)
                .courseGroup(courseGroup)
                .build();
    }

    public void updateByCreate() {
        this.user.updateByRegistrationCreditPlus(this.course.getCredit());
        this.course.updateByRegistrationCountPlus();
    }

    public void updateByDelete() {
        this.user.updateByRegistrationCreditMinus(this.course.getCredit());
        this.course.updateByRegistrationCountMinus();
    }
}