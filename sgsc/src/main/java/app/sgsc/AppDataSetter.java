package app.sgsc;

import app.sgsc.domain.service.logic.handler.CourseFileHandler;
import app.sgsc.domain.service.logic.handler.CourseGroupFileHandler;
import app.sgsc.domain.service.logic.handler.UserFileHandler;
import app.sgsc.domain.service.logic.helper.UserPasswordHelper;
import app.sgsc.global.common.service.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppDataSetter {
    private final UserPasswordHelper userPasswordHelper;
    private final UserFileHandler userFileHandler;
    private final CourseFileHandler courseFileHandler;
    private final CourseGroupFileHandler courseGroupFileHandler;

    @Order(value = 1)
    @EventListener(value = ApplicationReadyEvent.class)
    public void init() {
        // initUsers();
        // initCourseGroups();
        // initCourses();
    }

    private void initUsers() {
        userFileHandler.execute(FileUtils.toMultipartFile("user.csv", "src/main/resources/data/user.csv"), userPasswordHelper);
    }

    private void initCourseGroups() {
        courseGroupFileHandler.execute(FileUtils.toMultipartFile("course_group.csv", "src/main/resources/data/course_group.csv"));
    }

    private void initCourses() {
        courseFileHandler.execute(FileUtils.toMultipartFile("course.csv", "src/main/resources/data/course.csv"));
    }
}