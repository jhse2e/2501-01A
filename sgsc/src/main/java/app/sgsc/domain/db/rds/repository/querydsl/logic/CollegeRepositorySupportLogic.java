package app.sgsc.domain.db.rds.repository.querydsl.logic;

import app.sgsc.domain.db.rds.entity.College;
import app.sgsc.domain.db.rds.entity.QCollege;
import app.sgsc.domain.db.rds.repository.querydsl.logic.factory.CollegeQueryFactory;
import app.sgsc.domain.db.rds.repository.querydsl.CollegeRepositorySupport;
import app.sgsc.domain.dto.response.CollegeResultDto;
import app.sgsc.domain.dto.response.QCollegeResultDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
@SuppressWarnings(value = {"UnnecessaryLocalVariable"})
public class CollegeRepositorySupportLogic implements CollegeRepositorySupport {
    private final JPAQueryFactory queryFactory;
    private final QCollege college = QCollege.college;

    @Override
    @Transactional(readOnly = true)
    public Optional<College> getCollegeByCollegeNameAndCollegeNumber(String collegeName, String collegeNumber) {
        College queryResult = queryFactory.select(college)
                .from(college)
                .where(
                    CollegeQueryFactory.eqCollegeName(college, collegeName),
                    CollegeQueryFactory.eqCollegeNumber(college, collegeNumber))
                .fetchOne();

        return Optional.ofNullable(queryResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CollegeResultDto> getCollegeDtosBy() {
        List<CollegeResultDto> queryResults = queryFactory.select(new QCollegeResultDto(college))
                .from(college)
                .fetch();

        return queryResults;
    }
}