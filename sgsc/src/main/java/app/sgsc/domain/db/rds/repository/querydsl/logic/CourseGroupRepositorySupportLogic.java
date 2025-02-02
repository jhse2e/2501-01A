package app.sgsc.domain.db.rds.repository.querydsl.logic;

import app.sgsc.domain.db.rds.entity.*;
import app.sgsc.domain.db.rds.repository.querydsl.CourseGroupRepositorySupport;
import app.sgsc.domain.dto.response.CourseGroupResultDto;
import app.sgsc.domain.dto.response.QCourseGroupResultDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
@SuppressWarnings(value = {"UnnecessaryLocalVariable"})
public class CourseGroupRepositorySupportLogic implements CourseGroupRepositorySupport {
    private final JPAQueryFactory queryFactory;
    private final QCourseGroup courseGroup = QCourseGroup.courseGroup;
    private final QCollege college = QCollege.college;
    private final QCollegeDivision collegeDivision = QCollegeDivision.collegeDivision;
    private final QCollegeDepartment collegeDepartment = QCollegeDepartment.collegeDepartment;

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseGroup> getCourseGroupByCourseGroupNameAndCourseGroupName(String courseGroupName, String courseGroupNumber) {
        CourseGroup queryResult = queryFactory.select(courseGroup)
                .from(courseGroup)
                .where(eqCourseGroupName(courseGroupName),
                        eqCourseGroupNumber(courseGroupNumber))
                .fetchOne();

        return Optional.ofNullable(queryResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseGroupResultDto> getCourseGroupResultDtosBy(Long collegeId, Long collegeDivisionId, Long collegeDepartmentId) {
        List<CourseGroupResultDto> queryResults = queryFactory.select(new QCourseGroupResultDto(courseGroup))
                .from(courseGroup)
                .leftJoin(college).on(college.id.eq(courseGroup.college.id))
                .leftJoin(collegeDivision).on(collegeDivision.college.id.eq(courseGroup.college.id))
                .leftJoin(collegeDepartment).on(collegeDepartment.college.id.eq(courseGroup.college.id))
                .where(eqCollegeIdFromCourseGroup(collegeId),
                        eqCollegeDivisionIdFromCourseGroup(collegeDivisionId),
                        eqCollegeDepartmentIdFromCourseGroup(collegeDepartmentId))
                .distinct()
                .fetch();

        return queryResults;
    }

    private BooleanExpression eqCourseGroupId(Long collegeCourseId) {
        if (ObjectUtils.isEmpty(collegeCourseId)) {
            return null;
        }

        return courseGroup.id.eq(collegeCourseId);
    }

    private BooleanExpression eqCourseGroupName(String collegeCourseName) {
        if (!StringUtils.hasText(collegeCourseName)) {
            return null;
        }

        return courseGroup.name.eq(collegeCourseName);
    }

    private BooleanExpression eqCourseGroupNumber(String collegeCourseNumber) {
        if (!StringUtils.hasText(collegeCourseNumber)) {
            return null;
        }

        return courseGroup.number.eq(collegeCourseNumber);
    }

    private BooleanExpression eqCollegeIdFromCourseGroup(Long collegeId) {
        if (ObjectUtils.isEmpty(collegeId)) {
            return null;
        }

        return courseGroup.college.id.eq(collegeId);
    }

    private BooleanExpression eqCollegeDivisionIdFromCourseGroup(Long collegeDivisionId) {
        if (ObjectUtils.isEmpty(collegeDivisionId)) {
            return null;
        }

        return courseGroup.collegeDivision.id.eq(collegeDivisionId);
    }

    private BooleanExpression eqCollegeDepartmentIdFromCourseGroup(Long collegeDepartmentId) {
        if (ObjectUtils.isEmpty(collegeDepartmentId)) {
            return null;
        }

        return courseGroup.collegeDepartment.id.eq(collegeDepartmentId);
    }
}