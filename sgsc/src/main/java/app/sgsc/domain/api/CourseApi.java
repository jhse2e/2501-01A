package app.sgsc.domain.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CourseApi {

    /**
     * course.csv 파일을 사용해서 강의를 등록한다.
     */
    @Operation(summary = "강의 생성", description = "강의를 생성한다.")
    ResponseEntity<?> createCourses(
        MultipartFile courseFile
    );

    /**
     * course_group.csv 파일을 사용해서 강의 그룹을 등록한다.
     */
    @Operation(summary = "강의 그룹 생성", description = "강의 그룹을 생성한다.")
    ResponseEntity<?> createCourseGroups(
        MultipartFile courseGroupFile
    );

    @Operation(summary = "강의 목록 조회", description = "강의 목록을 조회한다.")
    ResponseEntity<?> getCourses(
        Long collegeId,
        Long collegeDivisionId,
        Long collegeDepartmentId,
        String courseYear,
        String courseSemester,
        String courseType
    );

    @Operation(summary = "강의 그룹 목록 조회", description = "강의 그룹 목록을 조회한다.")
    ResponseEntity<?> getCourseGroups(
        Long collegeId,
        Long collegeDivisionId,
        Long collegeDepartmentId
    );

    @Operation(summary = "수강 신청 목록 조회", description = "수강 신청 목록을 조회한다.")
    ResponseEntity<?> getCourseRegistrations(
        // ...
    );

    @Operation(summary = "수강 신청 시간표 목록 조회", description = "수강 신청 시간표 목록을 조회한다.")
    ResponseEntity<?> getCourseRegistrationTimetables(
        // ...
    );

    @Operation(summary = "예비 수강 신청 목록 조회", description = "예비 수강 신청 목록을 조회한다.")
    ResponseEntity<?> getCourseRegistrationCarts(
        // ...
    );

    @Operation(summary = "수강 신청 생성", description = "수강 신청을 생성한다.")
    ResponseEntity<?> createCourseRegistration(
        Long courseId
    );

    @Operation(summary = "예비 수강 신청 생성", description = "예비 수강 신청을 생성한다.")
    ResponseEntity<?> createCourseRegistrationCart(
        Long courseId
    );

    @Operation(summary = "수강 신청 삭제", description = "수강 신청을 삭제한다.")
    ResponseEntity<?> deleteCourseRegistration(
        Long courseId
    );

    @Operation(summary = "예비 수강 신청 삭제", description = "예비 수강 신청을 삭제한다.")
    ResponseEntity<?> deleteCourseRegistrationCart(
        Long courseId
    );
}