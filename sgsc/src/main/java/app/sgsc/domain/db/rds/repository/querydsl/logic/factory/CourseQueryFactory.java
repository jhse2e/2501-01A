package app.sgsc.domain.db.rds.repository.querydsl.logic.factory;

import app.sgsc.domain.db.rds.entity.*;
import app.sgsc.global.common.db.rds.JpaQuerydslQueryUtils;
import app.sgsc.global.common.db.rds.repository.AbstractQueryFactory;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourseQueryFactory extends AbstractQueryFactory {
    protected CourseQueryFactory(JPAQuery<?> query) {
        super(query);
    }

    public static CourseQueryFactory query(JPAQueryFactory query) {
        return new CourseQueryFactory(query.query());
    }

    public CourseQueryFactory from(QCourse course) {
        queryMixin.from(course);

        return this;
    }

    public CourseQueryFactory join(QCourse course, QCourseGroup courseGroup) {
        queryMixin.join(courseGroup).on(courseGroup.id.eq(course.courseGroup.id)); // 외래키(연관 관계) 없이 조인할 때 join() 사용법
        // queryMixin.join(course.courseGroup, courseGroup); // 외래키(연관 관계)로 조인할 때 join() 사용법

        return this;
    }

    public CourseQueryFactory join(QCourse course, QCollege college) {
        queryMixin.join(college).on(college.id.eq(course.courseGroup.college.id)); // courseGroup 테이블은 college 테이블과 필수 관계이므로 innerJoin()

        return this;
    }

    public CourseQueryFactory leftJoin(QCourse course, QCollegeDivision collegeDivision) {
        queryMixin.leftJoin(collegeDivision).on(collegeDivision.id.eq(course.courseGroup.collegeDivision.id)); // courseGroup 테이블은 collegeDivision 테이블과 선택 관계이므로 leftJoin()

        return this;
    }

    public CourseQueryFactory leftJoin(QCourse course, QCollegeDepartment collegeDepartment) {
        queryMixin.leftJoin(collegeDepartment).on(collegeDepartment.id.eq(course.courseGroup.collegeDepartment.id)); // courseGroup 테이블은 collegeDepartment 테이블과 선택 관계이므로 leftJoin()

        return this;
    }

    public static BooleanExpression eqCourseType(QCourse course, String courseType) {
        // return (courseType != null) ? course.type.eq(courseType) : null;
        return JpaQuerydslQueryUtils.eq(course.type, courseType);
    }

    public static BooleanExpression eqCourseYear(QCourse course, String courseYear) {
        // return (courseYear != null) ? course.year.eq(courseYear) : null;
        return JpaQuerydslQueryUtils.eq(course.year, courseYear);
    }

    public static BooleanExpression eqCourseSemester(QCourse course, String courseSemester) {
        // return (courseSemester != null) ? course.semester.eq(courseSemester) : null;
        return JpaQuerydslQueryUtils.eq(course.semester, courseSemester);
    }

    public static BooleanExpression eqCollegeId(QCollege college, Long collegeId) {
        // return (collegeId != null) ? college.id.eq(collegeId) : null;
        return JpaQuerydslQueryUtils.eq(college.id, collegeId);
    }

    public static BooleanExpression eqCollegeDivisionId(QCollegeDivision collegeDivision, Long collegeDivisionId) {
        // return (collegeDivisionId != null) ? collegeDivision.id.eq(collegeDivisionId) : null;
        return JpaQuerydslQueryUtils.eq(collegeDivision.id, collegeDivisionId);
    }

    public static BooleanExpression eqCollegeDepartmentId(QCollegeDepartment collegeDepartment, Long collegeDepartmentId) {
        // return (collegeDepartmentId != null) ? collegeDepartment.id.eq(collegeDepartmentId) : null;
        return JpaQuerydslQueryUtils.eq(collegeDepartment.id, collegeDepartmentId);
    }

    public static Predicate eqCourseConditions(QCourse course, String courseType, String courseYear, String courseSemester) {
        return JpaQuerydslQueryUtils.conditions(
            JpaQuerydslQueryUtils.eq(course.type, courseType),
            JpaQuerydslQueryUtils.eq(course.year, courseYear),
            JpaQuerydslQueryUtils.eq(course.semester, courseSemester)
        );
    }

    public static Predicate eqCollegeConditions(QCollege college, QCollegeDivision collegeDivision, QCollegeDepartment collegeDepartment, Long collegeId, Long collegeDivisionId, Long collegeDepartmentId) {
        return JpaQuerydslQueryUtils.conditions(
            JpaQuerydslQueryUtils.eq(college.id, collegeId),
            JpaQuerydslQueryUtils.eq(collegeDivision.id, collegeDivisionId),
            JpaQuerydslQueryUtils.eq(collegeDepartment.id, collegeDepartmentId)
        );
    }
}