package app.sgsc.domain.service.logic.handler;

import app.sgsc.domain.db.rds.entity.*;
import app.sgsc.domain.db.rds.repository.query.*;
import app.sgsc.domain.dto.request.*;
import app.sgsc.global.common.service.FileUtils;
import app.sgsc.global.configuration.exception.ApiException;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseFileHandler {
    private final CourseRepository courseRepository;
    private final CourseGroupRepository courseGroupRepository;
    private final CollegeRepository collegeRepository;
    private final CollegeDivisionRepository collegeDivisionRepository;
    private final CollegeDepartmentRepository collegeDepartmentRepository;

    public void execute(MultipartFile courseFile) {
        try {
            log.info("{}", "Course File");
            CSVReader reader = new CSVReader(new FileReader(FileUtils.toFile(courseFile)));
            reader.readNext();
            String[] data;

            while ((data = reader.readNext()) != null) {
                College college = null;
                CollegeDivision collegeDivision = null;
                CollegeDepartment collegeDepartment = null;
                CourseGroup courseGroup = null;
                Course course = null;

                // log.info("{}", Arrays.toString(data));

                if (!data[2].isBlank() && !data[3].isBlank()) {
                    college = getCollege(new CollegeDto(data[3], data[2]));
                }
                if (!data[4].isBlank() && !data[5].isBlank()) {
                    collegeDivision = getCollegeDivision(new CollegeDivisionDto(data[5], data[4]));
                }
                if (!data[6].isBlank() && !data[7].isBlank()) {
                    collegeDepartment = getCollegeDepartment(new CollegeDepartmentDto(data[7], data[6]));
                }
                if (!data[8].isBlank() && !data[9].isBlank()) {
                    courseGroup = getCourseGroup(new CourseGroupDto(data[9], data[8]));
                }
                if (!data[10].isBlank() && !data[11].isBlank() && !data[12].isBlank() && !data[13].isBlank() && !data[15].isBlank() && !data[16].isBlank() && !data[17].isBlank() && !data[18].isBlank()) {
                    course = getOrCreateCourse(courseGroup, new CourseDto(data[10], data[12], data[13], data[0], data[1], Integer.valueOf(data[11]), Integer.valueOf(data[15]), Integer.valueOf(data[16]), Integer.valueOf(data[17]), Integer.valueOf(data[18])));
                }
            }
            log.info("{}", "Course File Completed");
        } catch (IOException | CsvValidationException e) {
            log.info(e.getMessage());
        }
    }

    private College getCollege(CollegeDto collegeDto) {
        return collegeRepository.getCollegeByCollegeNameAndCollegeNumber(collegeDto.collegeName(), collegeDto.collegeNumber())
                .orElseThrow(() -> ApiException.of404(ApiExceptionType.COLLEGE_NOT_EXISTED));
    }

    private CollegeDivision getCollegeDivision(CollegeDivisionDto collegeDivisionDto) {
        return collegeDivisionRepository.getCollegeDivisionByCollegeDivisionNameAndCollegeDivisionNumber(collegeDivisionDto.collegeDivisionName(), collegeDivisionDto.collegeDivisionNumber())
                .orElseThrow(() -> ApiException.of404(ApiExceptionType.COLLEGE_DIVISION_NOT_EXISTED));
    }

    private CollegeDepartment getCollegeDepartment(CollegeDepartmentDto collegeDepartmentDto) {
        return collegeDepartmentRepository.getCollegeDepartmentByCollegeDepartmentNameAndCollegeDepartmentNumber(collegeDepartmentDto.collegeDepartmentName(), collegeDepartmentDto.collegeDepartmentNumber())
                .orElseThrow(() -> ApiException.of404(ApiExceptionType.COLLEGE_DEPARTMENT_NOT_EXISTED));
    }

    private CourseGroup getCourseGroup(CourseGroupDto courseGroupDto) {
        return courseGroupRepository.getCourseGroupByCourseGroupNameAndCourseGroupName(courseGroupDto.courseGroupName(), courseGroupDto.courseGroupNumber())
                .orElseThrow(() -> ApiException.of404(ApiExceptionType.COURSE_GROUP_NOT_EXISTED));
    }

    private Course getOrCreateCourse(CourseGroup courseGroup, CourseDto courseDto) {
        return courseRepository.getCourseByCourseNumber(courseDto.courseNumber())
                .orElseGet(() -> courseRepository.save(Course.create(courseDto.courseType(), courseDto.courseNumber(), courseDto.courseTimetable(), courseDto.courseYear(), courseDto.courseSemester(), courseDto.courseCredit(), courseDto.courseRegistrationCount(), courseDto.courseRegistrationCountCart(), courseDto.courseRegistrationCountLeft(), courseDto.courseRegistrationCountLimit(), courseGroup)));
    }
}