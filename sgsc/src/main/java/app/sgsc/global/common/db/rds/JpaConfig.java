package app.sgsc.global.common.db.rds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@Configuration
@EnableJpaAuditing
// @EnableJpaRepositories(basePackages = {"app.sgsc.domain.*.db.rds.repository"})
@EnableJpaRepositories(basePackages = {"app.sgsc.domain.db.rds.repository"}, repositoryImplementationPostfix = "Logic")
public class JpaConfig {
    // ...
}