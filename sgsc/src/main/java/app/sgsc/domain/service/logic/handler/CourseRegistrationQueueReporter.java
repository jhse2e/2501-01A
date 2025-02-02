package app.sgsc.domain.service.logic.handler;

import app.sgsc.domain.enumeration.CourseRegistrationStatusType;
import app.sgsc.domain.db.rds.entity.Course;
import app.sgsc.domain.db.rds.entity.CourseRegistration;
import app.sgsc.domain.db.redis.repository.CourseRegistrationRedisRepository;
import app.sgsc.domain.dto.request.CourseRegistrationQueueDto;
import app.sgsc.domain.dto.response.CourseRegistrationQueueResultDto;
import app.sgsc.global.Logger;
import app.sgsc.global.common.db.redis.type.RedisKeyType;
import app.sgsc.global.configuration.exception.ApiException;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRegistrationQueueReporter {
    private final ObjectMapper objectMapper;
    private final CourseRegistrationOperator courseRegistrationOperator;
    private final CourseRegistrationCounter courseRegistrationCounter;
    private final CourseRegistrationRedisRepository courseRegistrationRedisRepository;

    /**
     * 대기열에 수강 신청 결과를 생성한다.
     */
    public void setResult() {
        // 대기열을 조회한다. (100개 조회)
        Set<String> elements = courseRegistrationRedisRepository.getRangeByZSetOps(RedisKeyType.getCourseRegistrationOrderKey(), 0L, 100L - 1L);

        // 대기열 항목을 확인한다.
        for (String element : elements) { // ex) element: { userId: 1, courseId: 1 }
            Long queryResult = courseRegistrationRedisRepository.deleteByZSetOps(RedisKeyType.getCourseRegistrationOrderKey(), element);

            if (queryResult == null) {
                Logger.error(ApiExceptionType.COMMON_REDIS_QUERY_RESULT_NOT_VALID);
            } else {
                try {
                    CourseRegistrationQueueDto queueDto = objectMapper.readValue(element, CourseRegistrationQueueDto.class);
                    CourseRegistrationQueueResultDto queueResultDto = validate(queueDto);
                    courseRegistrationRedisRepository.setByZSetOps(RedisKeyType.getCourseRegistrationKey(), objectMapper.writeValueAsString(queueResultDto));
                } catch (JsonProcessingException e) {
                    Logger.error(ApiExceptionType.COMMON_JSON_PROCESSING_FAILED);
                }
            }
        }
    }

    /**
     * 대기열에 수강 신청 순번 결과를 생성한다.
     */
    public void setOrderResult(Long userId, Long courseId) {
        try {
            CourseRegistrationQueueDto courseRegistrationQueueDto = new CourseRegistrationQueueDto(userId, courseId);
            courseRegistrationRedisRepository.setByZSetOps(RedisKeyType.getCourseRegistrationOrderKey(), objectMapper.writeValueAsString(courseRegistrationQueueDto));
        } catch (JsonProcessingException e) {
            Logger.error(ApiExceptionType.COMMON_JSON_PROCESSING_FAILED);
        }
    }

    /**
     * 강의 좌석수에 따라 수강 신청 결과를 확인하고 신청 완료된 경우 강의 좌석수를 갱신한다.
     */
    private CourseRegistrationQueueResultDto validate(CourseRegistrationQueueDto courseRegistrationDto) {
        Long userId = courseRegistrationDto.userId();
        Long courseId = courseRegistrationDto.courseId();

        // 커넥션을 확인한다.
        if (courseRegistrationRedisRepository.isShutdown()) {
            courseRegistrationCounter.synchronizeCourseSeatCount();
        }

        // 강의 좌석수가 캐싱되어 있는지 확인한다.
        if (courseRegistrationRedisRepository.hasKeyBy(RedisKeyType.getCourseSeatKey(courseId))) {
            // 캐싱되어 있지만 강의 좌석수가 부족한 경우 CLOSED 상태를 세팅한다.
            if ((Integer.parseInt(courseRegistrationRedisRepository.getByValueOps(RedisKeyType.getCourseSeatKey(courseId))) <= 0)) {
                return new CourseRegistrationQueueResultDto(userId, courseId, CourseRegistrationStatusType.CLOSED.getValue());
            }
        }

        // 수강 신청 로직을 실행한다. (로직을 실행하면 강의 좌석수는 1 차감된다.)
        CourseRegistration courseRegistration;
        try {
            courseRegistration = courseRegistrationOperator.create(courseRegistrationDto.userId(), courseRegistrationDto.courseId());
        } catch (ApiException e) {
            // 수강 신청 처리 중에 문제가 생긴 경우 실패 메시지를 세팅한다.
            return new CourseRegistrationQueueResultDto(userId, courseId, e.getExceptionType().getMessage());
        } catch (Exception e) {
            // 수강 신청 처리 중에 문제가 생긴 경우 FAILED 상태를 세팅한다.
            return new CourseRegistrationQueueResultDto(userId, courseId, CourseRegistrationStatusType.FAILED.getValue());
        }

        Course course = courseRegistration.getCourse();
        // 강의 좌석수가 캐싱되어 있는지 확인한다.
        if (courseRegistrationRedisRepository.hasKeyBy(RedisKeyType.getCourseSeatKey(courseId))) {
            // 캐싱되어 있는 경우 강의 좌석수를 갱신한다.
            courseRegistrationCounter.cacheCourseSeatCountMinus(courseId);
        } else {
            // 캐싱되어 있지 않은 경우 강의 좌석수를 캐싱한다.
            courseRegistrationCounter.cacheCourseSeatCount(courseId, course.getRegistrationCountLeft());
        }

        // COMPLETED 상태를 세팅한다.
        return new CourseRegistrationQueueResultDto(userId, courseId, CourseRegistrationStatusType.COMPLETED.getValue());
    }
}