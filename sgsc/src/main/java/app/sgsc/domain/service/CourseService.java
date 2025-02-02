package app.sgsc.domain.service;

import app.sgsc.domain.dto.response.CourseGroupResultDto;
import app.sgsc.domain.dto.response.CourseResultDto;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface CourseService {

    /**
     * 강의를 생성한다.
     */
    void createCourses(
        MultipartFile courseFile
    );

    /**
     * 강의 그룹을 생성한다.
     */
    void createCourseGroups(
        MultipartFile courseGroupFile
    );

    /**
     * 강의 목록을 조회한다.
     */
    List<CourseResultDto> getCourses(
        Long collegeId,
        Long collegeDivisionId,
        Long collegeDepartmentId,
        String courseType,
        String courseYear,
        String courseSemester
    );

    /**
     * 강의 그룹 목록을 조회한다.
     */
    List<CourseGroupResultDto> getCourseGroups(
        Long collegeId,
        Long collegeDivisionId,
        Long collegeDepartmentId
    );
}