package app.sgsc.domain.db.rds.repository.querydsl.logic;

import app.sgsc.domain.db.rds.entity.CollegeDepartment;
import app.sgsc.domain.db.rds.entity.QCollege;
import app.sgsc.domain.db.rds.entity.QCollegeDepartment;
import app.sgsc.domain.db.rds.entity.QCollegeDivision;
import app.sgsc.domain.db.rds.repository.querydsl.logic.factory.CollegeQueryFactory;
import app.sgsc.domain.db.rds.repository.querydsl.CollegeDepartmentRepositorySupport;
import app.sgsc.domain.dto.response.CollegeDepartmentResultDto;
import app.sgsc.domain.dto.response.QCollegeDepartmentResultDto;
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
public class CollegeDepartmentRepositorySupportLogic implements CollegeDepartmentRepositorySupport {
    private final JPAQueryFactory queryFactory;
    private final QCollege college = QCollege.college;
    private final QCollegeDivision collegeDivision = QCollegeDivision.collegeDivision;
    private final QCollegeDepartment collegeDepartment = QCollegeDepartment.collegeDepartment;

    @Override
    @Transactional(readOnly = true)
    public Optional<CollegeDepartment> getCollegeDepartmentByCollegeDepartmentNameAndCollegeDepartmentNumber(String collegeDepartmentName, String collegeDepartmentNumber) {
        CollegeDepartment queryResult = queryFactory.select(collegeDepartment)
                .from(collegeDepartment)
                .where(
                    CollegeQueryFactory.eqCollegeDepartmentName(collegeDepartment, collegeDepartmentName),
                    CollegeQueryFactory.eqCollegeDepartmentNumber(collegeDepartment, collegeDepartmentNumber))
                .fetchOne();

        return Optional.ofNullable(queryResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CollegeDepartmentResultDto> getCollegeDepartmentResultDtosByCollegeId(Long collegeId) {
        List<CollegeDepartmentResultDto> queryResults = queryFactory.select(new QCollegeDepartmentResultDto(collegeDepartment))
                .from(collegeDepartment)
                .leftJoin(college).on(college.id.eq(collegeDepartment.college.id))
                .where(
                    CollegeQueryFactory.eqCollegeId(college, collegeId))
                .fetch();

        return queryResults;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CollegeDepartmentResultDto> getCollegeDepartmentResultDtosByCollegeId(Long collegeId, Long collegeDivisionId) {
        List<CollegeDepartmentResultDto> queryResults = queryFactory.select(new QCollegeDepartmentResultDto(collegeDepartment))
                .from(collegeDepartment)
                .leftJoin(college).on(college.id.eq(collegeDepartment.college.id))
                .where(
                    CollegeQueryFactory.eqCollegeId(college, collegeId))
                .fetch();

        return queryResults;
    }
}