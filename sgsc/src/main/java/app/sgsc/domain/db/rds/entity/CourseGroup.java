package app.sgsc.domain.db.rds.entity;

import app.sgsc.global.common.db.rds.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 강의 그룹
 */
@Getter
@Entity
@Table(
    name = "course_group",
    indexes = {
        // @Index(name = "course_group_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // 강의 그룹 번호는 고유해야 한다.
        @UniqueConstraint(name = "course_group_uk_1", columnNames = {"number"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuppressWarnings(value = "DefaultAnnotationParam")
public class CourseGroup extends AbstractEntity implements Serializable {

    /**
     * 강의 그룹 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 강의 그룹 이름
     */
    @Column(nullable = false)
    private String name;

    /**
     * 강의 그룹 번호
     */
    @Column(nullable = false)
    private String number;

    /**
     * 대학
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private College college;

    /**
     * 대학 학부
     */
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private CollegeDivision collegeDivision;

    /**
     * 대학 학과
     */
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private CollegeDepartment collegeDepartment;

    @Builder(access = AccessLevel.PUBLIC)
    public CourseGroup(Long id, String name, String number, College college, CollegeDivision collegeDivision, CollegeDepartment collegeDepartment) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.college = college;
        this.collegeDivision = collegeDivision;
        this.collegeDepartment = collegeDepartment;
    }

    public static CourseGroup create(String name, String number, College college) {
        return CourseGroup.builder()
                .name(name)
                .number(number)
                .college(college)
                .build();
    }

    public static CourseGroup create(String name, String number, College college, CollegeDivision collegeDivision) {
        return CourseGroup.builder()
                .name(name)
                .number(number)
                .college(college)
                .collegeDivision(collegeDivision)
                .build();
    }

    public static CourseGroup create(String name, String number, College college, CollegeDepartment collegeDepartment) {
        return CourseGroup.builder()
                .name(name)
                .number(number)
                .college(college)
                .collegeDepartment(collegeDepartment)
                .build();
    }

    public static CourseGroup create(String name, String number, College college, CollegeDivision collegeDivision, CollegeDepartment collegeDepartment) {
        return CourseGroup.builder()
                .name(name)
                .number(number)
                .college(college)
                .collegeDivision(collegeDivision)
                .collegeDepartment(collegeDepartment)
                .build();
    }
}