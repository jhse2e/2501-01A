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
 * 대학
 */
@Getter
@Entity
@Table(
    name = "college",
    indexes = {
        // @Index(name = "college_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // 대학 이름 + 대학 번호는 고유해야 한다. (단과 대학은 폐지된 후, 같은 이름과 다른 번호로 다시 개설될 수 있다.)
        @UniqueConstraint(name = "college_uk_1", columnNames = {"name", "number"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class College extends AbstractEntity implements Serializable {

    /**
     * 대학 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 대학 이름
     */
    @Column(nullable = false)
    private String name;

    /**
     * 대학 번호
     */
    @Column(nullable = false)
    private String number;

    @Builder(access = AccessLevel.PUBLIC)
    public College(Long id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public static College create(String collegeName, String collegeNumber) {
        return College.builder()
                .name(collegeName)
                .number(collegeNumber)
                .build();
    }
}