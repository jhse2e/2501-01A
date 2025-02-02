package app.sgsc.domain.service.logic;

import app.sgsc.domain.db.rds.repository.query.CourseGroupRepository;
import app.sgsc.domain.db.rds.repository.query.CourseRepository;
import app.sgsc.domain.dto.response.CourseGroupResultDto;
import app.sgsc.domain.dto.response.CourseResultDto;
import app.sgsc.domain.service.CourseService;
import app.sgsc.domain.service.logic.handler.CourseFileHandler;
import app.sgsc.domain.service.logic.handler.CourseGroupFileHandler;
import app.sgsc.global.configuration.exception.ApiException;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceLogic implements CourseService {
    private final CourseFileHandler courseFileHandler;
    private final CourseGroupFileHandler courseGroupFileHandler;
    private final CourseRepository courseRepository;
    private final CourseGroupRepository courseGroupRepository;

    @Override
    @Transactional
    public void createCourses(MultipartFile courseFile) {
        courseFileHandler.execute(courseFile);
    }

    @Override
    @Transactional
    public void createCourseGroups(MultipartFile courseGroupFile) {
        courseGroupFileHandler.execute(courseGroupFile);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResultDto> getCourses(Long collegeId, Long collegeDivisionId, Long collegeDepartmentId, String courseType, String courseYear, String courseSemester) {
        // 대학 ID 값을 확인한다.
        if (collegeId == null) {
            throw ApiException.of400(ApiExceptionType.COURSE_SEARCH_COLLEGE_ID_NOT_VALID);
        }

        // 강의 구분 값을 확인한다.
        if (!StringUtils.hasText(courseType)) {
            throw ApiException.of400(ApiExceptionType.COURSE_SEARCH_COURSE_TYPE_NOT_VALID);
        }

        // 개설 학기 값을 확인한다.
        if (!StringUtils.hasText(courseSemester)) {
            throw ApiException.of400(ApiExceptionType.COURSE_SEARCH_COURSE_SEMESTER_NOT_VALID);
        }

        // 개설 연도 값을 확인한다.
        if (!StringUtils.hasText(courseYear)) {
            throw ApiException.of400(ApiExceptionType.COURSE_SEARCH_COURSE_YEAR_NOT_VALID);
        }

        return courseRepository.getCourseResultDtosBy(collegeId, collegeDivisionId, collegeDepartmentId, courseType, courseYear, courseSemester);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseGroupResultDto> getCourseGroups(Long collegeId, Long collegeDivisionId, Long collegeDepartmentId) {
        // 대학 ID 값을 확인한다.
        if (collegeId == null) {
            throw ApiException.of400(ApiExceptionType.COURSE_SEARCH_COLLEGE_ID_NOT_VALID);
        }

        return courseGroupRepository.getCourseGroupResultDtosBy(collegeId, collegeDivisionId, collegeDepartmentId);
    }
}