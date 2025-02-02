package app.sgsc.domain.db.rds.repository.query;

import app.sgsc.domain.db.rds.entity.CourseRegistrationCart;
import app.sgsc.domain.db.rds.repository.querydsl.CourseRegistrationCartRepositorySupport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRegistrationCartRepository extends JpaRepository<CourseRegistrationCart, Long>, CourseRegistrationCartRepositorySupport {
    // ...
}