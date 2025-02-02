package app.sgsc.global.common.db.redis.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RedisScriptType {
    COURSE_SEAT_COUNT_PLUS(),
    COURSE_SEAT_COUNT_MINUS(),
}