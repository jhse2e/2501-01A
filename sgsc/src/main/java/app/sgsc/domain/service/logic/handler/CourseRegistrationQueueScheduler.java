package app.sgsc.domain.service.logic.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseRegistrationQueueScheduler {
    private final CourseRegistrationQueueReader courseRegistrationQueueReader;
    private final CourseRegistrationQueueReporter courseRegistrationQueueReporter;

    /**
     * 수강 신청 결과를 생성한다.
     */
    @Scheduled(fixedDelay = 500)
    @SchedulerLock(
        name = "course_registration_lock",
        lockAtLeastFor = "4s",
        lockAtMostFor = "8s"
    )
    public void setResult() {
        courseRegistrationQueueReporter.setResult();
    }

    /**
     * 수강 신청 결과를 조회한다.
     */
    @Scheduled(fixedDelay = 100)
    public void getResult() {
        courseRegistrationQueueReader.getResult();
    }

    /**
     * 수강 신청 순번 결과를 조회한다.
     */
    @Scheduled(fixedDelay = 1000)
    public void getOrderResult() {
        courseRegistrationQueueReader.getOrderResult();
    }
}