package app.sgsc.global.common.db.rds;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import java.util.Arrays;
import java.util.Objects;

public class JpaQuerydslQueryUtils {
    public static <T> BooleanExpression eq(SimpleExpression<T> left, T right) {
        return (right != null) ? left.eq(right) : null;
    }

    public static <T extends Number & Comparable<?>> BooleanExpression loe(NumberExpression<T> left, T right) {
        return (right != null) ? left.loe(right) : null;
    }

    public static <T extends Number & Comparable<?>> BooleanExpression goe(NumberExpression<T> left, T right) {
        return (right != null) ? left.goe(right) : null;
    }

    /**
     * 표현식을 AND 연산으로 조합한다.
     * @param predicates Expression (SimpleExpression, NumberExpression, BooleanExpression, ...)
     */
    public static Predicate conditions(Predicate... predicates) {
        BooleanBuilder builder = new BooleanBuilder();

        Arrays.stream(predicates).filter(Objects::nonNull).forEach(builder::and);

        return builder.getValue();
    }
}