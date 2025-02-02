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
 * 대학 학부
 */
@Getter
@Entity
@Table(
    name = "college_division",
    indexes = {
        // @Index(name = "college_division_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // 대학 학부 이름 + 대학 학부 번호는 고유해야 한다. (대학 학부는 폐지된 후, 같은 이름과 다른 번호로 다시 개설될 수 있다.)
        @UniqueConstraint(name = "college_division_uk_1", columnNames = {"name", "number"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CollegeDivision extends AbstractEntity implements Serializable {

    /**
     * 대학 학부 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 대학 학부 이름
     */
    @Column(nullable = false)
    private String name;

    /**
     * 대학 학부 번호
     */
    @Column(nullable = false)
    private String number;

    /**
     * 대학
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private College college;

    @Builder(access = AccessLevel.PUBLIC)
    public CollegeDivision(Long id, String name, String number, College college) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.college = college;
    }

    public static CollegeDivision create(String name, String number, College college) {
        return CollegeDivision.builder()
                .name(name)
                .number(number)
                .college(college)
                .build();
    }
}