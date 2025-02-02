package app.sgsc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AppRouter {

    /**
     * 메인 페이지
     */
    @GetMapping(value = {"/"})
    public String indexPage() {
        return "/page/index";
    }

    /**
     * 강의 페이지
     */
    @GetMapping(value = {"/course"})
    public String coursePage() {
        return "/page/course/index";
    }

    /**
     * 수강 신청 페이지
     */
    @GetMapping(value = {"/course/registration"})
    public String courseRegistrationPage() {
        return "/page/courseRegistration/index";
    }

    /**
     * 예비 수강 신청 페이지
     */
    @GetMapping(value = {"/course/registration/cart"})
    public String courseRegistrationCartPage() {
        return "/page/courseRegistrationCart/index";
    }

    /**
     * 시간표 페이지
     */
    @GetMapping(value = {"/course/registration/timetable"})
    public String timetablePage() {
        return "/page/courseRegistrationTimetable/index";
    }
}