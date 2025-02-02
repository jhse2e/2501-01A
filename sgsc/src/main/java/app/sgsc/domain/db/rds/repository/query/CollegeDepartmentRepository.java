package app.sgsc.domain.db.rds.repository.query;

import app.sgsc.domain.db.rds.entity.CollegeDepartment;
import app.sgsc.domain.db.rds.repository.querydsl.CollegeDepartmentRepositorySupport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeDepartmentRepository extends JpaRepository<CollegeDepartment, Long>, CollegeDepartmentRepositorySupport {
    // ...
}