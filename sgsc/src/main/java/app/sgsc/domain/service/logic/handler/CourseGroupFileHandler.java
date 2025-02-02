package app.sgsc.domain.service.logic.handler;

import app.sgsc.domain.db.rds.entity.College;
import app.sgsc.domain.db.rds.entity.CollegeDepartment;
import app.sgsc.domain.db.rds.entity.CollegeDivision;
import app.sgsc.domain.db.rds.entity.CourseGroup;
import app.sgsc.domain.db.rds.repository.query.CollegeDepartmentRepository;
import app.sgsc.domain.db.rds.repository.query.CollegeDivisionRepository;
import app.sgsc.domain.db.rds.repository.query.CollegeRepository;
import app.sgsc.domain.db.rds.repository.query.CourseGroupRepository;
import app.sgsc.domain.dto.request.CollegeDepartmentDto;
import app.sgsc.domain.dto.request.CollegeDivisionDto;
import app.sgsc.domain.dto.request.CollegeDto;
import app.sgsc.domain.dto.request.CourseGroupDto;
import app.sgsc.global.common.service.FileUtils;
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
public class CourseGroupFileHandler {
    private final CourseGroupRepository courseGroupRepository;
    private final CollegeRepository collegeRepository;
    private final CollegeDivisionRepository collegeDivisionRepository;
    private final CollegeDepartmentRepository collegeDepartmentRepository;

    public void execute(MultipartFile courseGroupFile) {
        try {
            log.info("{}", "Course Group File");
            CSVReader reader = new CSVReader(new FileReader(FileUtils.toFile(courseGroupFile)));
            reader.readNext();
            String[] data;

            while ((data = reader.readNext()) != null) {
                College college = null;
                CollegeDivision collegeDivision = null;
                CollegeDepartment collegeDepartment = null;
                CourseGroup courseGroup = null;

                // log.info("{}", Arrays.toString(data));

                if (!data[0].isBlank() && !data[1].isBlank()) {
                    college = getOrCreateCollege(new CollegeDto(data[1], data[0]));
                }
                if (!data[2].isBlank() && !data[3].isBlank()) {
                    collegeDivision = getOrCreateCollegeDivision(college, new CollegeDivisionDto(data[3], data[2]));
                }
                if (!data[4].isBlank() && !data[5].isBlank()) {
                    collegeDepartment = getOrCreateCollegeDepartment(college, new CollegeDepartmentDto(data[5], data[4]));
                }
                if (!data[6].isBlank() && !data[7].isBlank()) {
                    if ((data[2].isBlank() && data[3].isBlank()) && (data[4].isBlank() && data[5].isBlank())) {
                        courseGroup = getOrCreateCourseGroup(college, new CourseGroupDto(data[7], data[6]));
                    } if ((!data[2].isBlank() && !data[3].isBlank()) && (data[4].isBlank() && data[5].isBlank())) {
                        courseGroup = getOrCreateCourseGroup(college, collegeDivision, new CourseGroupDto(data[7], data[6]));
                    } if ((data[2].isBlank() && data[3].isBlank()) && (!data[4].isBlank() && !data[5].isBlank())) {
                        courseGroup = getOrCreateCourseGroup(college, collegeDepartment, new CourseGroupDto(data[7], data[6]));
                    } else {
                        courseGroup = getOrCreateCourseGroup(college, collegeDivision, collegeDepartment, new CourseGroupDto(data[7], data[6]));
                    }
                }
            }
            log.info("{}", "Course Group File Completed");
        } catch (IOException | CsvValidationException e) {
            log.info(e.getMessage());
        }
    }

    private College getOrCreateCollege(CollegeDto collegeDto) {
        return collegeRepository.getCollegeByCollegeNameAndCollegeNumber(collegeDto.collegeName(), collegeDto.collegeNumber())
                .orElseGet(() -> collegeRepository.save(College.create(collegeDto.collegeName(), collegeDto.collegeNumber())));
    }

    private CollegeDivision getOrCreateCollegeDivision(College college, CollegeDivisionDto collegeDivisionDto) {
        return collegeDivisionRepository.getCollegeDivisionByCollegeDivisionNameAndCollegeDivisionNumber(collegeDivisionDto.collegeDivisionName(), collegeDivisionDto.collegeDivisionNumber())
                .orElseGet(() -> collegeDivisionRepository.save(CollegeDivision.create(collegeDivisionDto.collegeDivisionName(), collegeDivisionDto.collegeDivisionNumber(), college)));
    }

    private CollegeDepartment getOrCreateCollegeDepartment(College college, CollegeDepartmentDto collegeDepartmentDto) {
        return collegeDepartmentRepository.getCollegeDepartmentByCollegeDepartmentNameAndCollegeDepartmentNumber(collegeDepartmentDto.collegeDepartmentName(), collegeDepartmentDto.collegeDepartmentNumber())
                .orElseGet(() -> collegeDepartmentRepository.save(CollegeDepartment.create(collegeDepartmentDto.collegeDepartmentName(), collegeDepartmentDto.collegeDepartmentNumber(), college)));
    }

    private CourseGroup getOrCreateCourseGroup(College college, CourseGroupDto courseGroupDto) {
        return courseGroupRepository.getCourseGroupByCourseGroupNameAndCourseGroupName(courseGroupDto.courseGroupName(), courseGroupDto.courseGroupNumber())
                .orElseGet(() -> courseGroupRepository.save(CourseGroup.create(courseGroupDto.courseGroupName(), courseGroupDto.courseGroupNumber(), college)));
    }

    private CourseGroup getOrCreateCourseGroup(College college, CollegeDivision collegeDivision, CourseGroupDto courseGroupDto) {
        return courseGroupRepository.getCourseGroupByCourseGroupNameAndCourseGroupName(courseGroupDto.courseGroupName(), courseGroupDto.courseGroupNumber())
                .orElseGet(() -> courseGroupRepository.save(CourseGroup.create(courseGroupDto.courseGroupName(), courseGroupDto.courseGroupNumber(), college, collegeDivision)));
    }

    private CourseGroup getOrCreateCourseGroup(College college, CollegeDepartment collegeDepartment, CourseGroupDto courseGroupDto) {
        return courseGroupRepository.getCourseGroupByCourseGroupNameAndCourseGroupName(courseGroupDto.courseGroupName(), courseGroupDto.courseGroupNumber())
                .orElseGet(() -> courseGroupRepository.save(CourseGroup.create(courseGroupDto.courseGroupName(), courseGroupDto.courseGroupNumber(), college, collegeDepartment)));
    }

    private CourseGroup getOrCreateCourseGroup(College college, CollegeDivision collegeDivision, CollegeDepartment collegeDepartment, CourseGroupDto courseGroupDto) {
        return courseGroupRepository.getCourseGroupByCourseGroupNameAndCourseGroupName(courseGroupDto.courseGroupName(), courseGroupDto.courseGroupNumber())
                .orElseGet(() -> courseGroupRepository.save(CourseGroup.create(courseGroupDto.courseGroupName(), courseGroupDto.courseGroupNumber(), college, collegeDivision, collegeDepartment)));
    }
}