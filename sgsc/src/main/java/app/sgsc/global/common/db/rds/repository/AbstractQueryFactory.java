package app.sgsc.global.common.db.rds.repository;

import com.querydsl.jpa.impl.JPAQuery;

public abstract class AbstractQueryFactory {
    protected final JPAQuery<?> queryMixin;

    protected AbstractQueryFactory(JPAQuery<?> query) {
        this.queryMixin = query;
    }

    public JPAQuery<?> build() {
        return queryMixin;
    }
}