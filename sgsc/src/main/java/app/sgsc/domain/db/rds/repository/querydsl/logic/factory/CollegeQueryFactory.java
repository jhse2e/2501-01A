package app.sgsc.domain.db.rds.repository.querydsl.logic.factory;

import app.sgsc.domain.db.rds.entity.QCollege;
import app.sgsc.domain.db.rds.entity.QCollegeDepartment;
import app.sgsc.domain.db.rds.entity.QCollegeDivision;
import app.sgsc.global.common.db.rds.JpaQuerydslQueryUtils;
import app.sgsc.global.common.db.rds.repository.AbstractQueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollegeQueryFactory extends AbstractQueryFactory {
    protected CollegeQueryFactory(JPAQuery<?> query) {
        super(query);
    }

    public static CollegeQueryFactory query(JPAQueryFactory query) {
        return new CollegeQueryFactory(query.query());
    }

    public CollegeQueryFactory from(QCollege college) {
        queryMixin.from(college);

        return this;
    }

    public CollegeQueryFactory from(QCollegeDivision collegeDivision) {
        queryMixin.from(collegeDivision);

        return this;
    }

    public CollegeQueryFactory from(QCollegeDepartment collegeDepartment) {
        queryMixin.from(collegeDepartment);

        return this;
    }
    
    public static BooleanExpression eqCollegeId(QCollege college, Long collegeId) {
        return JpaQuerydslQueryUtils.eq(college.id, collegeId);
    }

    public static  BooleanExpression eqCollegeName(QCollege college, String collegeName) {
        return JpaQuerydslQueryUtils.eq(college.name, collegeName);
    }

    public static  BooleanExpression eqCollegeNumber(QCollege college, String collegeNumber) {
        return JpaQuerydslQueryUtils.eq(college.number, collegeNumber);
    }

    public static BooleanExpression eqCollegeDivisionName(QCollegeDivision collegeDivision, String collegeDivisionName) {
        return JpaQuerydslQueryUtils.eq(collegeDivision.name, collegeDivisionName);
    }

    public static BooleanExpression eqCollegeDivisionNumber(QCollegeDivision collegeDivision, String collegeDivisionNumber) {
        return JpaQuerydslQueryUtils.eq(collegeDivision.number, collegeDivisionNumber);
    }

    public static BooleanExpression eqCollegeDepartmentName(QCollegeDepartment collegeDepartment, String collegeDepartmentName) {
        return JpaQuerydslQueryUtils.eq(collegeDepartment.name, collegeDepartmentName);
    }

    public static BooleanExpression eqCollegeDepartmentNumber(QCollegeDepartment collegeDepartment, String collegeDepartmentNumber) {
        return JpaQuerydslQueryUtils.eq(collegeDepartment.number, collegeDepartmentNumber);
    }
}