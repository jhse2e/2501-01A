package app.sgsc.domain.db.rds.repository.query;

import app.sgsc.domain.db.rds.entity.CourseGroup;
import app.sgsc.domain.db.rds.repository.querydsl.CourseGroupRepositorySupport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseGroupRepository extends JpaRepository<CourseGroup, Long>, CourseGroupRepositorySupport {
    // ...
}