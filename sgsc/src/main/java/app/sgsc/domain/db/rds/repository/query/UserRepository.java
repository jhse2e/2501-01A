package app.sgsc.domain.db.rds.repository.query;

import app.sgsc.domain.db.rds.entity.User;
import app.sgsc.domain.db.rds.repository.querydsl.UserRepositorySupport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositorySupport {
    // ...
}