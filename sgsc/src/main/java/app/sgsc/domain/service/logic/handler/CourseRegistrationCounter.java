package app.sgsc.domain.service.logic.handler;

import app.sgsc.domain.db.rds.entity.Course;
import app.sgsc.domain.db.rds.repository.query.CourseRegistrationRepository;
import app.sgsc.domain.db.rds.repository.query.CourseRepository;
import app.sgsc.domain.db.redis.repository.CourseRegistrationRedisRepository;
import app.sgsc.global.common.db.redis.type.RedisKeyType;
import app.sgsc.global.common.db.redis.type.RedisScriptType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRegistrationCounter {
    private final CourseRepository courseRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseRegistrationRedisRepository courseRegistrationRedisRepository;

    /**
     * 해당 강의의 잔여 좌석을 n개 캐싱한다.
     */
    public void cacheCourseSeatCount(Long courseId, Integer registrationCountLeft) {
        courseRegistrationRedisRepository.setByValueOps(RedisKeyType.getCourseSeatKey(courseId), String.valueOf(registrationCountLeft));
    }
    
    /**
     * 해당 강의의 잔여 좌석을 1개 더해서 캐싱한다.
     */
    public void cacheCourseSeatCountPlus(Long courseId) {
        courseRegistrationRedisRepository.executeByScript(RedisKeyType.getCourseSeatKey(courseId), String.valueOf(1), RedisScriptType.COURSE_SEAT_COUNT_PLUS);
    }
    
    /**
     * 해당 강의의 잔여 좌석을 1개 빼서 캐싱한다.
     */
    public void cacheCourseSeatCountMinus(Long courseId) {
        courseRegistrationRedisRepository.executeByScript(RedisKeyType.getCourseSeatKey(courseId), String.valueOf(1), RedisScriptType.COURSE_SEAT_COUNT_MINUS);
    }

    /**
     * 수강 신청된 모든 강의의 잔여 좌석을 캐싱한다.
     */
    public void synchronizeCourseSeatCount() {
        for (Course course : courseRepository.getCoursesBy()) {
            String key = RedisKeyType.getCourseSeatKey(course.getId()); // course:seat:1
            String value = String.valueOf(course.getRegistrationCountLimit() - courseRegistrationRepository.getCourseRegistrationCountByCourseId(course.getId()));

            courseRegistrationRedisRepository.resetByValueOps(key, value);
        }
    }
}