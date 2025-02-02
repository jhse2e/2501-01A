package app.sgsc.domain.service.logic;

import app.sgsc.domain.service.CourseRegistrationCartService;
import app.sgsc.domain.service.logic.handler.CourseRegistrationCartOperator;
import app.sgsc.domain.service.logic.helper.CourseRegistrationCartHelper;
import app.sgsc.domain.db.rds.repository.query.CourseRegistrationCartRepository;
import app.sgsc.domain.dto.response.CourseRegistrationCartResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseRegistrationCartServiceLogic implements CourseRegistrationCartService {
    private final CourseRegistrationCartHelper courseRegistrationCartHelper;
    private final CourseRegistrationCartOperator courseRegistrationCartOperator;
    private final CourseRegistrationCartRepository courseRegistrationCartRepository;

    @Override
    public void register(Long userId, Long courseId) {
        // 예비 수강 신청 조건을 확인한다.
        courseRegistrationCartHelper.check(userId, courseId);
        // 예비 수강 신청을 처리한다.
        courseRegistrationCartOperator.create(userId, courseId);
    }

    @Override
    public void remove(Long userId, Long courseId) {
        // 예비 수강 신청을 삭제한다.
        courseRegistrationCartOperator.delete(userId, courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseRegistrationCartResultDto> getResultList(Long userId) {
        return courseRegistrationCartRepository.getCourseRegistrationCartResultDtosByUserId(userId);
    }
}