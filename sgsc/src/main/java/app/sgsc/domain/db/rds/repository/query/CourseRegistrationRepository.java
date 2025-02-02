package app.sgsc.domain.db.rds.repository.query;

import app.sgsc.domain.db.rds.entity.CourseRegistration;
import app.sgsc.domain.db.rds.repository.querydsl.CourseRegistrationRepositorySupport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long>, CourseRegistrationRepositorySupport {
    // ...
}