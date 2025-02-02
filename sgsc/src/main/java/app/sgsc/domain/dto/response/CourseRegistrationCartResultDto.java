package app.sgsc.domain.dto.response;

import app.sgsc.domain.db.rds.entity.*;
import com.querydsl.core.annotations.QueryProjection;
import java.io.Serializable;

public record CourseRegistrationCartResultDto(
    Long courseId,
    String courseType,
    String courseName,
    String courseNumber,
    String courseTimetable,
    Integer courseCredit,
    Integer courseRegistrationCount,
    Integer courseRegistrationCountCart,
    Integer courseRegistrationCountLeft,
    Integer courseRegistrationCountLimit,
    String collegeName,
    String collegeDivisionName,
    String collegeDepartmentName
) implements Serializable {

    @QueryProjection
    public CourseRegistrationCartResultDto {
        // ...
    }

    @QueryProjection
    public CourseRegistrationCartResultDto(Course course, CourseGroup courseGroup, College college, CollegeDivision collegeDivision, CollegeDepartment collegeDepartment) {
        this(course.getId(), course.getType(), courseGroup.getName(), course.getNumber(), course.getTimetable(), course.getCredit(), course.getRegistrationCount(), course.getRegistrationCountCart(), course.getRegistrationCountLeft(), course.getRegistrationCountLimit(), college.getName(), collegeDivision.getName(), collegeDepartment.getName());
    }
}