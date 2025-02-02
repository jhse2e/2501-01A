package app.sgsc.domain.service.logic.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRegistrationMessageHelper {
    private final SimpMessageSendingOperations messageOperations;
    private static final String COURSE_REGISTRATION_SOCKET_URL = "/ws-sub/course-registration/%d";
    private static final String COURSE_REGISTRATION_ORDER_SOCKET_URL = "/ws-sub/course-registration-order/%d";

    /**
     * 메시지를 전송한다.
     */
    public void messageBy(String path, String payload) {
        messageOperations.convertAndSend(path, payload);
    }

    /**
     * 수강 신청 결과 메시지를 전송한다.
     */
    public void messageByGetResult(Long userId, String payload) {
        messageBy(String.format(COURSE_REGISTRATION_SOCKET_URL, userId), payload);
    }

    /**
     * 수강 신청 결과 에러 메시지를 전송한다.
     */
    public void messageByGetErrorResult(Long userId, String payload) {
        messageBy(String.format(COURSE_REGISTRATION_SOCKET_URL, userId), payload);
    }

    /**
     * 수강 신청 순번 결과 메시지를 전송한다.
     */
    public void messageByGetOrderResult(Long userId, String payload) {
        messageBy(String.format(COURSE_REGISTRATION_ORDER_SOCKET_URL, userId), payload);
    }

    /**
     * 수강 신청 순번 결과 에러 메시지를 전송한다.
     */
    public void messageByGetOrderErrorResult(Long userId, String payload) {
        messageBy(String.format(COURSE_REGISTRATION_ORDER_SOCKET_URL, userId), payload);
    }
}