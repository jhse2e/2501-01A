package app.sgsc.domain.db.rds.repository.querydsl.logic;

import app.sgsc.domain.db.rds.entity.*;
import app.sgsc.domain.db.rds.repository.querydsl.CourseRegistrationRepositorySupport;
import app.sgsc.domain.dto.response.CourseRegistrationResultDto;
import app.sgsc.domain.dto.response.QCourseRegistrationResultDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
@SuppressWarnings(value = {"UnnecessaryLocalVariable"})
public class CourseRegistrationRepositorySupportLogic implements CourseRegistrationRepositorySupport {
    private final JPAQueryFactory queryFactory;
    private final QUser user = QUser.user;
    private final QCourse course = QCourse.course;
    private final QCourseGroup courseGroup = QCourseGroup.courseGroup;
    private final QCollege college = QCollege.college;
    private final QCollegeDivision collegeDivision = QCollegeDivision.collegeDivision;
    private final QCollegeDepartment collegeDepartment = QCollegeDepartment.collegeDepartment;
    private final QCourseRegistration courseRegistration = QCourseRegistration.courseRegistration;
    private final QCourseRegistrationCart courseRegistrationCart = QCourseRegistrationCart.courseRegistrationCart;

    @Override
    @Transactional(readOnly = true)
    public Boolean hasByUserIdAndCourseId(Long userId, Long courseId) {
        Integer queryResult = queryFactory.selectOne().from(courseRegistration)
                .join(user).on(user.id.eq(courseRegistration.user.id))
                .join(course).on(course.id.eq(courseRegistration.course.id))
                .where(user.id.eq(userId),
                        course.id.eq(courseId))
                .fetchFirst();

        return queryResult != null;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean hasByUserIdAndCourseGroupId(Long userId, Long courseGroupId) {
        Integer queryResult = queryFactory.selectOne().from(courseRegistration)
                .join(user).on(user.id.eq(courseRegistration.user.id))
                .join(course).on(course.id.eq(courseRegistration.course.id))
                .join(courseGroup).on(courseGroup.id.eq(course.courseGroup.id))
                .where(user.id.eq(userId),
                        courseGroup.id.eq(courseGroupId))
                .fetchFirst();

        return queryResult != null;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getCourseRegistrationCountByCourseId(Long courseId) {
        Integer queryResult = queryFactory.selectOne().from(courseRegistration)
                .join(course).on(course.id.eq(courseRegistration.course.id))
                .where(courseRegistration.course.id.eq(courseId))
                .fetchFirst();

        return queryResult;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseRegistration> getCourseRegistrationByUserIdAndCourseId(Long userId, Long courseId) {
        CourseRegistration queryResult = queryFactory.select(courseRegistration)
                .from(courseRegistration)
                .join(user).on(user.id.eq(courseRegistration.user.id))
                .join(course).on(course.id.eq(courseRegistration.course.id))
                .where(user.id.eq(userId),
                        course.id.eq(courseId))
                .fetchFirst();

        return Optional.ofNullable(queryResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseRegistration> getCourseRegistrationsByUserId(Long userId) {
        List<CourseRegistration> queryResults = queryFactory.select(courseRegistration)
                .from(courseRegistration)
                .join(user).on(user.id.eq(courseRegistration.user.id))
                .where(user.id.eq(userId))
                .fetch();

        return queryResults;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseRegistrationResultDto> getCourseRegistrationDtosByUserId(Long userId) {
        List<CourseRegistrationResultDto> queryResults = queryFactory.select(new QCourseRegistrationResultDto(
                    course.id,
                    course.type,
                    courseGroup.name,
                    course.number,
                    course.timetable,
                    course.credit,
                    course.registrationCount,
                    course.registrationCountCart,
                    course.registrationCountLeft,
                    course.registrationCountLimit,
                    college.name,
                    collegeDivision.name.coalesce("-"),
                    collegeDepartment.name.coalesce("-")))
                .from(courseRegistration)
                .join(user).on(user.id.eq(courseRegistration.user.id))
                .join(course).on(course.id.eq(courseRegistration.course.id))
                .join(courseGroup).on(courseGroup.id.eq(courseRegistration.courseGroup.id))
                .join(college).on(college.id.eq(courseGroup.college.id))
                .leftJoin(collegeDivision).on(collegeDivision.id.eq(courseGroup.collegeDivision.id))
                .leftJoin(collegeDepartment).on(collegeDepartment.id.eq(courseGroup.collegeDepartment.id))
                .where(courseRegistration.user.id.eq(userId))
                .orderBy(courseRegistration.course.id.asc())
                .fetch();

        return queryResults;
    }
}