package app.sgsc.domain.db.redis.repository.logic;

import app.sgsc.domain.db.redis.repository.CourseRegistrationRedisRepository;
import app.sgsc.global.common.db.redis.type.RedisScriptType;
import app.sgsc.global.configuration.exception.ApiException;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.Set;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CourseRegistrationRedisRepositoryLogic implements CourseRegistrationRedisRepository {
    private final RedisTemplate<String, String> redisQueryFactory;
    private final RedisScript<Boolean> redisScriptOfCourseSeatCountPlus;
    private final RedisScript<Boolean> redisScriptOfCourseSeatCountMinus;

    @Override
    public Boolean isShutdown() {
        return redisQueryFactory.execute(RedisConnection::isClosed);
    }

    @Override
    public Boolean hasKeyBy(String key) {
        return redisQueryFactory.hasKey(key);
    }

    @Override
    public void setByValueOps(String key, String value) {
        // if (Objects.equals(redisQueryFactory.hasKey(key), Boolean.TRUE)) {
        //     throw ApiException.of409(ApiExceptionType.REDIS_KEY_EXISTED);
        // }

        redisQueryFactory.opsForValue().set(key, value);
    }

    @Override
    public void resetByValueOps(String key, String value) {
        // if (Objects.equals(redisQueryFactory.hasKey(key), Boolean.FALSE)) {
        //     throw ApiException.of409(ApiExceptionType.REDIS_KEY_NOT_EXISTED);
        // }

        redisQueryFactory.opsForValue().set(key, value);
    }

    @Override
    public String getByValueOps(String key) {
        return redisQueryFactory.opsForValue().get(key);
    }

    @Override
    public Boolean setByZSetOps(String key, String value) {
        return redisQueryFactory.opsForZSet().add(key, value, System.currentTimeMillis());
    }

    @Override
    public Long getRankByZSetOps(String key, String value) {
        return redisQueryFactory.opsForZSet().rank(key, value);
    }

    @Override
    public Set<String> getRangeByZSetOps(String key, Long rangeStart, Long rangeStop) {
        return redisQueryFactory.opsForZSet().range(key, rangeStart, rangeStop);
    }

    @Override
    public Long deleteByZSetOps(String key, String value) {
        return redisQueryFactory.opsForZSet().remove(key, value);
    }

    @Override
    public Boolean executeByScript(String key, String value, RedisScriptType type) {
        if (type.equals(RedisScriptType.COURSE_SEAT_COUNT_PLUS)) {
            return redisQueryFactory.execute(redisScriptOfCourseSeatCountPlus, Collections.singletonList(key), value);
        } else if (type.equals(RedisScriptType.COURSE_SEAT_COUNT_MINUS)) {
            return redisQueryFactory.execute(redisScriptOfCourseSeatCountMinus, Collections.singletonList(key), value);
        } else {
            throw ApiException.of500(ApiExceptionType.COMMON_REDIS_QUERY_SCRIPT_NOT_SUPPORTED);
        }
    }
}