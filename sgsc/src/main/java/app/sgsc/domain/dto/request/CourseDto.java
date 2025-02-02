package app.sgsc.domain.dto.request;

import java.io.Serializable;

/**
 * @param courseType 강의 구분
 * @param courseNumber 강의 번호
 * @param courseTimetable 강의 시간표
 * @param courseYear 강의 개설 연도
 * @param courseSemester 강의 개설 학기
 * @param courseCredit 강의 학점
 * @param courseRegistrationCount 수강 신청 인원
 * @param courseRegistrationCountCart 수강 신청 장바구니 인원
 * @param courseRegistrationCountLeft 수강 신청 잔여 인원
 * @param courseRegistrationCountLimit 수강 신청 제한 인원
 */
public record CourseDto(
    String courseType,
    String courseNumber,
    String courseTimetable,
    String courseYear,
    String courseSemester,
    Integer courseCredit,
    Integer courseRegistrationCount,
    Integer courseRegistrationCountCart,
    Integer courseRegistrationCountLeft,
    Integer courseRegistrationCountLimit
) implements Serializable {
    // ...
}