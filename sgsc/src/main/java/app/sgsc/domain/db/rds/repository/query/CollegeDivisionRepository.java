package app.sgsc.domain.db.rds.repository.query;

import app.sgsc.domain.db.rds.entity.CollegeDivision;
import app.sgsc.domain.db.rds.repository.querydsl.CollegeDivisionRepositorySupport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeDivisionRepository extends JpaRepository<CollegeDivision, Long>, CollegeDivisionRepositorySupport {
    // ...
}