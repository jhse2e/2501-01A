package app.sgsc.domain.api.logic;

import app.sgsc.domain.api.CourseApi;
import app.sgsc.domain.dto.response.*;
import app.sgsc.domain.service.CourseRegistrationCartService;
import app.sgsc.domain.service.CourseRegistrationService;
import app.sgsc.domain.service.CourseService;
import app.sgsc.global.common.api.ApiResponse;
import app.sgsc.global.configuration.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CourseApiLogic implements CourseApi {
    private final CourseService courseService;
    private final CourseRegistrationService courseRegistrationService;
    private final CourseRegistrationCartService courseRegistrationCartService;

    @Override
    @PostMapping(value = {"/api/courses"})
    public ResponseEntity<?> createCourses(
        @RequestPart(name = "courseFile", required = true) MultipartFile courseFile
    ) {
        courseService.createCourses(courseFile);

        return ApiResponse.ok();
    }

    @Override
    @PostMapping(value = {"/api/courses/groups"})
    public ResponseEntity<?> createCourseGroups(
        @RequestPart(name = "courseGroupFile", required = true) MultipartFile courseGroupFile
    ) {
        courseService.createCourseGroups(courseGroupFile);

        return ApiResponse.ok();
    }

    @Override
    @GetMapping(value = {"/api/courses"})
    public ResponseEntity<?> getCourses(
        @RequestParam(name = "collegeId", required = true) Long collegeId,
        @RequestParam(name = "collegeDivisionId", required = false) Long collegeDivisionId,
        @RequestParam(name = "collegeDepartmentId", required = false) Long collegeDepartmentId,
        @RequestParam(name = "courseType", required = true) String courseType,
        @RequestParam(name = "courseYear", required = true) String courseYear,
        @RequestParam(name = "courseSemester", required = true) String courseSemester
    ) {
        List<CourseResultDto> apiResult = courseService.getCourses(collegeId, collegeDivisionId, collegeDepartmentId, courseType, courseYear, courseSemester);

        return ApiResponse.ok(apiResult);
    }

    @Override
    @GetMapping(value = {"/api/courses/groups"})
    public ResponseEntity<?> getCourseGroups(
        @RequestParam(value = "collegeId", required = true) Long collegeId,
        @RequestParam(value = "collegeDivisionId", required = false) Long collegeDivisionId,
        @RequestParam(value = "collegeDepartmentId", required = false) Long collegeDepartmentId
    ) {
        List<CourseGroupResultDto> apiResult = courseService.getCourseGroups(collegeId, collegeDivisionId, collegeDepartmentId);

        return ApiResponse.ok(apiResult);
    }

    @Override
    @GetMapping(value = {"/api/courses/registrations"})
    public ResponseEntity<?> getCourseRegistrations(
        // ...
    ) {
        List<CourseRegistrationResultDto> apiResult = courseRegistrationService.getResultList(SecurityUtils.getUserId());

        return ApiResponse.ok(apiResult);
    }

    @Override
    @GetMapping(value = {"/api/courses/registrations/timetables"})
    public ResponseEntity<?> getCourseRegistrationTimetables(
        // ...
    ) {
        List<CourseRegistrationTimetableResultDto> apiResult = courseRegistrationService.getTimetableList(SecurityUtils.getUserId());

        return ApiResponse.ok(apiResult);
    }

    @Override
    @GetMapping(value = {"/api/courses/registrations/carts"})
    public ResponseEntity<?> getCourseRegistrationCarts(
        // ...
    ) {
        List<CourseRegistrationCartResultDto> apiResult = courseRegistrationCartService.getResultList(SecurityUtils.getUserId());

        return ApiResponse.ok(apiResult);
    }

    @Override
    @PostMapping(value = {"/api/courses/registrations"})
    public ResponseEntity<?> createCourseRegistration(
        @RequestParam(name = "courseId", required = true) Long courseId
    ) {
        courseRegistrationService.register(SecurityUtils.getUserId(), courseId);

        return ApiResponse.ok();
    }

    @Override
    @PostMapping(value = {"/api/courses/registrations/carts"})
    public ResponseEntity<?> createCourseRegistrationCart(
        @RequestParam(name = "courseId", required = true) Long courseId
    ) {
        courseRegistrationCartService.register(SecurityUtils.getUserId(), courseId);

        return ApiResponse.ok();
    }

    @Override
    @DeleteMapping(value = {"/api/courses/registrations"})
    public ResponseEntity<?> deleteCourseRegistration(
        @RequestParam(name = "courseId", required = true) Long courseId
    ) {
        courseRegistrationService.remove(SecurityUtils.getUserId(), courseId);

        return ApiResponse.ok();
    }

    @Override
    @DeleteMapping(value = {"/api/courses/registrations/carts"})
    public ResponseEntity<?> deleteCourseRegistrationCart(
        @RequestParam(name = "courseId", required = true) Long courseId
    ) {
        courseRegistrationCartService.remove(SecurityUtils.getUserId(), courseId);

        return ApiResponse.ok();
    }
}