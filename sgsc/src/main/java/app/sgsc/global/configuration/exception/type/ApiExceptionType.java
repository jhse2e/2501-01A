package app.sgsc.global.configuration.exception.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiExceptionType {

    /* 기타 */
    COMMON("-100", ""),
    COMMON_FILE_NOT_VALID("-110", ""),
    COMMON_CONTENT_TYPE_NOT_VALID("-111", ""),
    COMMON_CONTENT_TYPE_NOT_SUPPORTED("-112", ""),
    COMMON_JSON_MAPPING_FAILED("-120", ""),
    COMMON_JSON_PROCESSING_FAILED("-121", ""),
    COMMON_REDIS_QUERY_RESULT_NOT_VALID("-130", ""),
    COMMON_REDIS_QUERY_SCRIPT_NOT_VALID("-131", ""),
    COMMON_REDIS_QUERY_SCRIPT_NOT_SUPPORTED("-132", ""),
    COMMON_SOCKET_MESSAGE_SENDING_FAILED("-140", ""),
    COMMON_SOCKET_MESSAGE_RECEIVING_FAILED("-141", ""),

    /* 사용자 관련 */
    USER_EXISTED("-200", ""),
    USER_NOT_EXISTED("-201", "사용자를 찾을 수 없습니다."),
    USER_AUTHENTICATION_FAILED("-210", ""),
    USER_AUTHORIZATION_NOT_VALID("-211", ""),
    USER_AUTHENTICATION_NOT_VALID("-212", ""),
    USER_AUTHENTICATION_TOKEN_NOT_VALID("-213", ""),

    /* 대학, 학부, 학과 관련 */
    COLLEGE_EXISTED("-300", ""),
    COLLEGE_NOT_EXISTED("-301", "단과 대학을 찾을 수 없습니다."),
    COLLEGE_DIVISION_EXISTED("-310", ""),
    COLLEGE_DIVISION_NOT_EXISTED("-311", "학부를 찾을 수 없습니다."),
    COLLEGE_DEPARTMENT_EXISTED("-320", ""),
    COLLEGE_DEPARTMENT_NOT_EXISTED("-321", "학과를 찾을 수 없습니다."),
    COURSE_GROUP_EXISTED("-330", ""),
    COURSE_GROUP_NOT_EXISTED("-331", "강의 그룹을 찾을 수 없습니다."),
    COURSE_EXISTED("-340", ""),
    COURSE_NOT_EXISTED("-341", "강의를 찾을 수 없습니다."),

    /* 수강 신청 관련 */
    COURSE_REGISTRATION_NOT_EXISTED("-400", "수강 신청 내역이 없습니다."),
    COURSE_REGISTRATION_COURSE_DUPLICATED("-401", "동일한 강의를 신청할 수 없습니다."),
    COURSE_REGISTRATION_COURSE_GROUP_DUPLICATED("-402", "동일한 강의 그룹을 신청할 수 없습니다."),
    COURSE_REGISTRATION_COURSE_TIMETABLE_DUPLICATED("-403", "동일한 시간대의 강의를 신청할 수 없습니다."),
    COURSE_REGISTRATION_COUNT_NOT_VALID("-404", "수강 신청 인원을 확인해주세요."),
    COURSE_REGISTRATION_CART_NOT_EXISTED("-410", "예비 수강 신청 내역이 없습니다."),
    COURSE_REGISTRATION_CART_COURSE_DUPLICATED("-411", "동일한 강의를 신청할 수 없습니다."),
    COURSE_REGISTRATION_CART_COUNT_NOT_VALID("-412", "예비 수강 신청 인원을 확인해주세요."),
    COURSE_REGISTRATION_CREDIT_NOT_VALID("-420", "신청 학점을 확인해주세요."),
    COURSE_REGISTRATION_CREDIT_LEFT_NOT_VALID("-421", "신청할 수 있는 학점이 부족합니다."),
    COURSE_REGISTRATION_CREDIT_LIMIT_NOT_VALID("-422", "신청할 수 있는 학점을 초과하였습니다."),

    /* 강의 검색 관련 */
    COURSE_SEARCH_COLLEGE_ID_NOT_VALID("-500", "대학을 선택해주세요."),
    COURSE_SEARCH_COURSE_YEAR_NOT_VALID("-501", "개설 연도를 선택해주세요."),
    COURSE_SEARCH_COURSE_SEMESTER_NOT_VALID("-502", "개설 학기를 선택해주세요."),
    COURSE_SEARCH_COURSE_TYPE_NOT_VALID("-503", "강의 구분을 선택해주세요."),
    ;

    private final String code;
    private final String message;
}