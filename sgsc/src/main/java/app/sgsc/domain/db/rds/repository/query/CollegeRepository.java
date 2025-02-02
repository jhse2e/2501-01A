package app.sgsc.domain.db.rds.repository.query;

import app.sgsc.domain.db.rds.entity.College;
import app.sgsc.domain.db.rds.repository.querydsl.CollegeRepositorySupport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepository extends JpaRepository<College, Long>, CollegeRepositorySupport {
    // ...
}