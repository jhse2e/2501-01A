package app.sgsc.domain.db.rds.repository.querydsl.logic.factory;

import app.sgsc.domain.db.rds.entity.QCourseRegistration;
import app.sgsc.global.common.db.rds.repository.AbstractQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CourseRegistrationQueryFactory extends AbstractQueryFactory {
    protected CourseRegistrationQueryFactory(JPAQuery<?> query) {
        super(query);
    }

    public static CourseRegistrationQueryFactory query(JPAQueryFactory query) {
        return new CourseRegistrationQueryFactory(query.query());
    }

    public CourseRegistrationQueryFactory from(QCourseRegistration courseRegistration) {
        queryMixin.from(courseRegistration);

        return this;
    }
}