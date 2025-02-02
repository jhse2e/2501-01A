package app.sgsc.domain.db.rds.repository.querydsl.logic;

import app.sgsc.domain.db.rds.entity.CollegeDivision;
import app.sgsc.domain.db.rds.entity.QCollege;
import app.sgsc.domain.db.rds.entity.QCollegeDivision;
import app.sgsc.domain.db.rds.repository.querydsl.logic.factory.CollegeQueryFactory;
import app.sgsc.domain.db.rds.repository.querydsl.CollegeDivisionRepositorySupport;
import app.sgsc.domain.dto.response.CollegeDivisionResultDto;
import app.sgsc.domain.dto.response.QCollegeDivisionResultDto;
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
public class CollegeDivisionRepositorySupportLogic implements CollegeDivisionRepositorySupport {
    private final JPAQueryFactory queryFactory;
    private final QCollege college = QCollege.college;
    private final QCollegeDivision collegeDivision = QCollegeDivision.collegeDivision;

    @Override
    @Transactional(readOnly = true)
    public Optional<CollegeDivision> getCollegeDivisionByCollegeDivisionNameAndCollegeDivisionNumber(String collegeDivisionName, String collegeDivisionNumber) {
        CollegeDivision queryResult = queryFactory.select(collegeDivision)
                .from(collegeDivision)
                .where(
                    CollegeQueryFactory.eqCollegeDivisionName(collegeDivision, collegeDivisionName),
                    CollegeQueryFactory.eqCollegeDivisionNumber(collegeDivision, collegeDivisionNumber))
                .fetchOne();

        return Optional.ofNullable(queryResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CollegeDivisionResultDto> getCollegeDivisionResultDtosByCollegeId(Long collegeId) {
        List<CollegeDivisionResultDto> queryResults = queryFactory.select(new QCollegeDivisionResultDto(collegeDivision))
                .from(collegeDivision)
                .leftJoin(college).on(college.id.eq(collegeDivision.college.id))
                .where(
                    CollegeQueryFactory.eqCollegeId(college, collegeId))
                .fetch();

        return queryResults;
    }
}