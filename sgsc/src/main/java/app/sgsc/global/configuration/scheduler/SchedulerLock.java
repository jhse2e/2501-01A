package app.sgsc.global.configuration.scheduler;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 스케줄러 락
 */
@Getter
@Entity
@Table(
    name = "scheduler_lock",
    indexes = {
        // @Index(name = "scheduler_lock_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "scheduler_lock_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchedulerLock implements Serializable {

    /**
     * 스케줄러 락 이름
     */
    @Id
    @Column(nullable = false, columnDefinition = "VARCHAR(64)")
    private String name;

    /**
     * 스케줄러 락 사용자
     */
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String useBy;

    /**
     * 스케줄러 락 시작 날짜
     */
    @Column(nullable = false, columnDefinition = "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)")
    private LocalDateTime startAt;

    /**
     * 스케줄러 락 종료 날짜
     */
    @Column(nullable = false, columnDefinition = "TIMESTAMP(3)")
    private LocalDateTime stopAt;
}