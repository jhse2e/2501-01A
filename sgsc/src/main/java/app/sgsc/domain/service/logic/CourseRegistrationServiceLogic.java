package app.sgsc.domain.service.logic;

import app.sgsc.domain.dto.response.CourseRegistrationTimetableResultDto;
import app.sgsc.domain.service.CourseRegistrationService;
import app.sgsc.domain.service.logic.handler.CourseRegistrationOperator;
import app.sgsc.domain.service.logic.handler.CourseRegistrationCounter;
import app.sgsc.domain.service.logic.handler.CourseRegistrationQueueReporter;
import app.sgsc.domain.service.logic.helper.CourseRegistrationHelper;
import app.sgsc.domain.db.rds.repository.query.CourseRegistrationRepository;
import app.sgsc.domain.dto.response.CourseRegistrationResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseRegistrationServiceLogic implements CourseRegistrationService {
    private final CourseRegistrationOperator courseRegistrationOperator;
    private final CourseRegistrationHelper courseRegistrationHelper;
    private final CourseRegistrationCounter courseRegistrationCounter;
    private final CourseRegistrationQueueReporter courseRegistrationQueueReporter;
    private final CourseRegistrationRepository courseRegistrationRepository;

    @Override
    public void register(Long userId, Long courseId) {
        // 수강 신청 조건을 확인한다.
        courseRegistrationHelper.check(userId, courseId);
        // 수강 신청 순번을 대기열에 생성한다.
        courseRegistrationQueueReporter.setOrderResult(userId, courseId);
        // 스케줄러가 대기열을 확인하고 수강 신청을 생성한다.
        // courseRegistrationQueueScheduler.setResult();
    }

    @Override
    public void remove(Long userId, Long courseId) {
        // 수강 신청을 삭제한다.
        courseRegistrationOperator.delete(userId, courseId);
        // 수강 인원을 수정한다.
        courseRegistrationCounter.cacheCourseSeatCountPlus(courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseRegistrationResultDto> getResultList(Long userId) {
        return courseRegistrationRepository.getCourseRegistrationDtosByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseRegistrationTimetableResultDto> getTimetableList(Long userId) {
        return courseRegistrationRepository.getCourseRegistrationsByUserId(userId).stream().map(CourseRegistrationTimetableResultDto::of).toList();
    }
}