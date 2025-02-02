package app.sgsc.domain.service;

import app.sgsc.domain.dto.response.CourseRegistrationCartResultDto;
import java.util.List;

public interface CourseRegistrationCartService {

    /**
     * 예비 수강 신청을 생성한다.
     */
    void register(
        Long userId,
        Long courseId
    );

    /**
     * 예비 수강 신청을 삭제한다.
     */
    void remove(
        Long userId,
        Long courseId
    );

    /**
     * 예비 수강 신청 결과 목록을 조회한다.
     */
    List<CourseRegistrationCartResultDto> getResultList(
        Long userId
    );
}