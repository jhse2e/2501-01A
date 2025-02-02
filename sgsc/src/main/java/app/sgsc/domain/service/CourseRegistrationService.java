package app.sgsc.domain.service;

import app.sgsc.domain.dto.response.CourseRegistrationResultDto;
import app.sgsc.domain.dto.response.CourseRegistrationTimetableResultDto;
import java.util.List;

public interface CourseRegistrationService {

    /**
     * 수강 신청을 생성한다.
     */
    void register(
        Long userId,
        Long courseId
    );

    /**
     * 수강 신청을 삭제한다.
     */
    void remove(
        Long userId,
        Long courseId
    );

    /**
     * 수강 신청 결과 목록을 조회한다.
     */
    List<CourseRegistrationResultDto> getResultList(
        Long userId
    );

    /**
     * 수강 신청 시간표 목록을 조회한다.
     */
    List<CourseRegistrationTimetableResultDto> getTimetableList(
        Long userId
    );
}