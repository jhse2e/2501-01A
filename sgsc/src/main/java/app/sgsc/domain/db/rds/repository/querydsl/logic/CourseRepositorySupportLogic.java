package app.sgsc.domain.db.rds.repository.querydsl.logic;

import app.sgsc.domain.db.rds.entity.*;
import app.sgsc.domain.db.rds.repository.querydsl.CourseRepositorySupport;
import app.sgsc.domain.db.rds.repository.querydsl.logic.factory.CourseQueryFactory;
import app.sgsc.domain.dto.response.CourseResultDto;
import app.sgsc.domain.dto.response.QCourseResultDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
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
public class CourseRepositorySupportLogic implements CourseRepositorySupport {
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
    public Optional<Course> getCourseByCourseId(Long courseId) {
        Course queryResult = queryFactory.select(course)
                .from(course)
                .where(course.id.eq(courseId))
                .fetchOne();

        return Optional.ofNullable(queryResult);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> getCourseByCourseId(Long courseId, LockModeType lockModeType) {
        Course queryResult = queryFactory.select(course)
                .from(course)
                .where(course.id.eq(courseId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchOne();

        return Optional.ofNullable(queryResult);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> getCourseByCourseNumber(String courseNumber) {
        Course queryResult = queryFactory.select(course)
                .from(course)
                .where(course.number.eq(courseNumber))
                .fetchOne();

        return Optional.ofNullable(queryResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getCoursesBy() {
        List<Course> queryResults = queryFactory.select(course)
                .from(course)
                .join(courseRegistration).on(courseRegistration.course.id.eq(course.id))
                .distinct()
                .fetch();

        return queryResults;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResultDto> getCourseResultDtosBy(Long collegeId, Long collegeDivisionId, Long collegeDepartmentId, String courseType, String courseYear, String courseSemester) {
        List<CourseResultDto> queryResults = CourseQueryFactory.query(queryFactory)
                .from(course)
                .join(course, courseGroup)
                .join(course, college)
                .leftJoin(course, collegeDivision)
                .leftJoin(course, collegeDepartment)
                .build().select(new QCourseResultDto(
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
                    collegeDepartment.name.coalesce("-")
                )).where(
                    CourseQueryFactory.eqCourseConditions(course, courseType, courseYear, courseSemester),
                    CourseQueryFactory.eqCollegeConditions(college, collegeDivision, collegeDepartment, collegeId, collegeDivisionId, collegeDepartmentId))
                .orderBy(course.id.asc())
                .fetch();

        return queryResults;
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<CourseResultDto> getCourseResultDtosBy(Long collegeId, Long collegeDivisionId, Long collegeDepartmentId, String courseType, String courseYear, String courseSemester) {
//        List<CourseResultDto> queryResults = queryFactory.select(new QCourseResultDto(
//                    course.id,
//                    course.type,
//                    courseGroup.name,
//                    course.number,
//                    course.timetable,
//                    course.credit,
//                    course.registrationCount,
//                    course.registrationCountCart,
//                    course.registrationCountLeft,
//                    course.registrationCountLimit,
//                    college.name,
//                    collegeDivision.name.coalesce("-"),
//                    collegeDepartment.name.coalesce("-")
//                ))
//                .from(course)
//                .join(course.courseGroup, courseGroup)
//                .join(college).on(college.id.eq(course.courseGroup.college.id))
//                .leftJoin(collegeDivision).on(collegeDivision.id.eq(course.courseGroup.collegeDivision.id))
//                .leftJoin(collegeDepartment).on(collegeDepartment.id.eq(course.courseGroup.collegeDepartment.id))
//                .where(
//                    eqCourseType(courseType),
//                    eqCourseYear(courseYear),
//                    eqCourseSemester(courseSemester),
//                    eqCollegeId(collegeId),
//                    eqCollegeDivisionId(collegeDivisionId),
//                    eqCollegeDepartmentId(collegeDepartmentId))
//                .orderBy(course.id.asc())
//                .fetch();
//
//        return queryResults;
//    }
}