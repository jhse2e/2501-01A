package app.sgsc.domain.dto.response;

import app.sgsc.domain.db.rds.entity.CourseRegistration;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public record CourseRegistrationTimetableResultDto(
    String courseName,
    List<String[]> courseTimetables
) implements Serializable {

    public static CourseRegistrationTimetableResultDto of(CourseRegistration courseRegistration) {
        String courseName = courseRegistration.getCourseGroup().getName();
        List<String[]> courseTimetables = new ArrayList<>();

        for (String dateTime : courseRegistration.getCourse().getTimetable().split(", ")) {
            String day = dateTime.split(" ")[0];
            String dayStartTime = dateTime.split(" ")[1].split("~")[0];
            String dayStopTime = dateTime.split(" ")[1].split("~")[1];
            courseTimetables.add(new String[]{day, dayStartTime, dayStopTime});
        }

        return new CourseRegistrationTimetableResultDto(courseName, courseTimetables);
    }
}