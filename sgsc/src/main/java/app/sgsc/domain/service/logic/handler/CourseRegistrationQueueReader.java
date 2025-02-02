package app.sgsc.domain.service.logic.handler;

import app.sgsc.domain.db.redis.repository.CourseRegistrationRedisRepository;
import app.sgsc.domain.dto.response.CourseRegistrationQueueOrderResultDto;
import app.sgsc.domain.dto.response.CourseRegistrationQueueResultDto;
import app.sgsc.domain.service.logic.helper.CourseRegistrationMessageHelper;
import app.sgsc.global.Logger;
import app.sgsc.global.common.db.redis.type.RedisKeyType;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRegistrationQueueReader {
    private final ObjectMapper objectMapper;
    private final CourseRegistrationMessageHelper courseRegistrationMessageHelper;
    private final CourseRegistrationRedisRepository courseRegistrationRedisRepository;

    /**
     * 대기열에서 수강 신청 결과를 조회한다.
     */
    public void getResult() {
        // 대기열을 조회한다. (전체 조회)
        Set<String> elements = courseRegistrationRedisRepository.getRangeByZSetOps(RedisKeyType.getCourseRegistrationKey(), 0L, -1L);

        // 대기열 항목을 확인한다.
        for (String element : elements) { // ex) element: { userId: 1, courseId: 1, message: COMPLETED|CLOSED }
            Long queryResult = courseRegistrationRedisRepository.deleteByZSetOps(RedisKeyType.getCourseRegistrationKey(), element);

            if (queryResult == null) {
                Logger.error(ApiExceptionType.COMMON_REDIS_QUERY_RESULT_NOT_VALID);
            } else {
                try {
                    CourseRegistrationQueueResultDto queueResultDto = objectMapper.readValue(element, CourseRegistrationQueueResultDto.class);
                    courseRegistrationMessageHelper.messageByGetResult(queueResultDto.userId(), queueResultDto.message()); // ex) payload: COMPLETED|CLOSED (신청 완료: COMPLETED, 신청 마감: CLOSED)
                } catch (JsonMappingException e) {
                    Logger.error(ApiExceptionType.COMMON_JSON_MAPPING_FAILED);
                } catch (JsonProcessingException e) {
                    Logger.error(ApiExceptionType.COMMON_JSON_PROCESSING_FAILED);
                } catch (MessagingException e) {
                    Logger.error(ApiExceptionType.COMMON_SOCKET_MESSAGE_SENDING_FAILED);
                }
            }
        }
    }

    /**
     * 대기열에서 수강 신청 순번 결과를 조회한다.
     */
    public void getOrderResult() {
        // 대기열을 조회한다. (전체 조회)
        Set<String> elements = courseRegistrationRedisRepository.getRangeByZSetOps(RedisKeyType.getCourseRegistrationOrderKey(), 0L, -1L);

        // 대기열 항목을 확인한다.
        for (String element : elements) { // ex) element: { userId: 1, courseId: 1 }
            Long queryResult = courseRegistrationRedisRepository.getRankByZSetOps(RedisKeyType.getCourseRegistrationOrderKey(), element);

            if (queryResult == null) {
                Logger.error(ApiExceptionType.COMMON_REDIS_QUERY_RESULT_NOT_VALID);
            } else {
                try {
                    CourseRegistrationQueueOrderResultDto queueOrderResultDto = objectMapper.readValue(element, CourseRegistrationQueueOrderResultDto.class);
                    courseRegistrationMessageHelper.messageByGetOrderResult(queueOrderResultDto.userId(), String.valueOf(queryResult)); // ex) payload: 10 (순번: 10번)
                } catch (JsonMappingException e) {
                    Logger.error(ApiExceptionType.COMMON_JSON_MAPPING_FAILED);
                } catch (JsonProcessingException e) {
                    Logger.error(ApiExceptionType.COMMON_JSON_PROCESSING_FAILED);
                } catch (MessagingException e) {
                    Logger.error(ApiExceptionType.COMMON_SOCKET_MESSAGE_SENDING_FAILED);
                }
            }
        }
    }
}