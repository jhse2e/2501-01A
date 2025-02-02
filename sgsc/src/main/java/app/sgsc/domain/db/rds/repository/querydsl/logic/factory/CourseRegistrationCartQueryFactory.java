package app.sgsc.domain.db.rds.repository.querydsl.logic.factory;

import app.sgsc.domain.db.rds.entity.*;
import app.sgsc.global.common.db.rds.JpaQuerydslQueryUtils;
import app.sgsc.global.common.db.rds.repository.AbstractQueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourseRegistrationCartQueryFactory extends AbstractQueryFactory {
    protected CourseRegistrationCartQueryFactory(JPAQuery<?> query) {
        super(query);
    }

    public static CourseRegistrationCartQueryFactory query(JPAQueryFactory query) {
        return new CourseRegistrationCartQueryFactory(query.query());
    }

    public CourseRegistrationCartQueryFactory from(QCourseRegistrationCart courseRegistrationCart) {
        queryMixin.from(courseRegistrationCart);

        return this;
    }

    public CourseRegistrationCartQueryFactory join(QUser user, QCourseRegistrationCart courseRegistrationCart) {
        queryMixin.join(user).on(user.id.eq(courseRegistrationCart.user.id));

        return this;
    }

    public CourseRegistrationCartQueryFactory join(QCourse course, QCourseRegistrationCart courseRegistrationCart) {
        queryMixin.join(course).on(course.id.eq(courseRegistrationCart.course.id));

        return this;
    }

    public CourseRegistrationCartQueryFactory join(QCourseGroup courseGroup, QCourseRegistrationCart courseRegistrationCart) {
        queryMixin.join(courseGroup).on(courseGroup.id.eq(courseRegistrationCart.course.courseGroup.id));

        return this;
    }

    public CourseRegistrationCartQueryFactory join(QCollege college, QCourseRegistrationCart courseRegistrationCart) {
        queryMixin.join(college).on(college.id.eq(courseRegistrationCart.course.courseGroup.college.id));

        return this;
    }

    public CourseRegistrationCartQueryFactory leftJoin(QCollegeDivision collegeDivision, QCourseRegistrationCart courseRegistrationCart) {
        queryMixin.leftJoin(collegeDivision).on(collegeDivision.id.eq(courseRegistrationCart.course.courseGroup.collegeDivision.id));

        return this;
    }

    public CourseRegistrationCartQueryFactory leftJoin(QCollegeDepartment collegeDepartment, QCourseRegistrationCart courseRegistrationCart) {
        queryMixin.leftJoin(collegeDepartment).on(collegeDepartment.id.eq(courseRegistrationCart.course.courseGroup.collegeDepartment.id));

        return this;
    }

    public static BooleanExpression eqUserId(QUser user, Long userId) {
        return JpaQuerydslQueryUtils.eq(user.id, userId);
    }

    public static BooleanExpression eqCourseId(QCourse course, Long courseId) {
        return JpaQuerydslQueryUtils.eq(course.id, courseId);
    }
}