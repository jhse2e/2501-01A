package app.sgsc.domain.db.redis.repository;

import app.sgsc.global.common.db.redis.type.RedisScriptType;
import java.util.Set;

public interface CourseRegistrationRedisRepository {

    /**
     * 커넥션 상태를 확인한다.
     */
    Boolean isShutdown(
        // ...
    );

    /**
     * 데이터 Key 값을 확인한다. (hasKey(K))
     */
    Boolean hasKeyBy(
        String key
    );

    /**
     * 데이터를 생성한다. (ValueOperations.set(K, V))
     */
    void setByValueOps(
        String key,
        String value
    );

    /**
     * 데이터를 수정한다. (ValueOperations.set(K, V))
     */
    void resetByValueOps(
        String key,
        String value
    );

    /**
     * 데이터를 조회한다. (ValueOperations.get(K))
     */
    String getByValueOps(
        String key
    );

    /**
     * 데이터를 생성한다. (ZSetOperations.add(K, V, score))
     */
    Boolean setByZSetOps(
        String key,
        String value
    );

    /**
     * 데이터를 순위 조회한다. (ZSetOperations.rank(K, V))
     */
    Long getRankByZSetOps(
        String key,
        String value
    );

    /**
     * 데이터를 범위 조회한다. (ZSetOperations.range(K, start, end))
     */
    Set<String> getRangeByZSetOps(
        String key,
        Long rangeStart,
        Long rangeStop
    );

    /**
     * 데이터를 삭제한다. (ZSetOperations.remove(K, V))
     */
    Long deleteByZSetOps(
        String key,
        String value
    );

    /**
     * 스크립트를 실행한다.
     */
    Boolean executeByScript(
        String key,
        String value,
        RedisScriptType type
    );
}