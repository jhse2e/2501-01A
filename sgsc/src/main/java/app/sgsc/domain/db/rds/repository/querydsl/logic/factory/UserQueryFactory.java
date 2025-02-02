package app.sgsc.domain.db.rds.repository.querydsl.logic.factory;

import app.sgsc.domain.db.rds.entity.QUser;
import app.sgsc.global.common.db.rds.JpaQuerydslQueryUtils;
import app.sgsc.global.common.db.rds.repository.AbstractQueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserQueryFactory extends AbstractQueryFactory {
    protected UserQueryFactory(JPAQuery<?> query) {
        super(query);
    }

    public static UserQueryFactory query(JPAQueryFactory query) {
        return new UserQueryFactory(query.query());
    }

    public static BooleanExpression eqUserId(QUser user, Long userId) {
        return JpaQuerydslQueryUtils.eq(user.id, userId);
    }

    public static BooleanExpression eqUserNumber(QUser user, String userNumber) {
        return JpaQuerydslQueryUtils.eq(user.number, userNumber);
    }
}