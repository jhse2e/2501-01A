package app.sgsc;

import app.sgsc.domain.db.rds.repository.query.CourseGroupRepository;
import app.sgsc.domain.db.rds.repository.query.CourseRepository;
import app.sgsc.domain.db.rds.repository.query.UserRepository;
import app.sgsc.domain.service.CourseRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppDataScheduler {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseGroupRepository courseGroupRepository;
    private final CourseRegistrationService courseRegistrationService;

    /**
     * 수강 신청을 세팅한다.
     */
//    @Scheduled(fixedDelay = 10)
//    public void setCourseRegistrations() {
//        if (userRepository.count() == 2250) {
//            if (courseGroupRepository.count() == 115) {
//                if (courseRepository.count() == 263) {
//                    long userId = ThreadLocalRandom.current().nextLong(2, 2251); // nextLong(10, 100) = 10~99
//                    long courseId = ThreadLocalRandom.current().nextLong(1, 264); // nextLong(10, 100) = 10~99
//                    courseRegistrationService.register(userId, courseId);
//                }
//            }
//        }
//    }
}