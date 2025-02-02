package app.sgsc.domain.db.rds.repository.query;

import app.sgsc.domain.db.rds.entity.Course;
import app.sgsc.domain.db.rds.repository.querydsl.CourseRepositorySupport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long>, CourseRepositorySupport {
    // ...
}