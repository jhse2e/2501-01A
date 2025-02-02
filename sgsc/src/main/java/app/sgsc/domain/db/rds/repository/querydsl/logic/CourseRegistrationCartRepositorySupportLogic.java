package app.sgsc.domain.db.rds.repository.querydsl.logic;

import app.sgsc.domain.db.rds.entity.*;
import app.sgsc.domain.db.rds.repository.querydsl.logic.factory.CourseRegistrationCartQueryFactory;
import app.sgsc.domain.db.rds.repository.querydsl.CourseRegistrationCartRepositorySupport;
import app.sgsc.domain.dto.response.CourseRegistrationCartResultDto;
import app.sgsc.domain.dto.response.QCourseRegistrationCartResultDto;
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
public class CourseRegistrationCartRepositorySupportLogic implements CourseRegistrationCartRepositorySupport {
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
    public Boolean hasCourseRegistrationCartByUserIdAndCourseId(Long userId, Long courseId) {
        Integer queryResult = (Integer) CourseRegistrationCartQueryFactory.query(queryFactory)
                .from(courseRegistrationCart)
                .join(user, courseRegistrationCart)
                .join(course, courseRegistrationCart)
                .build().where(
                    CourseRegistrationCartQueryFactory.eqUserId(user, userId),
                    CourseRegistrationCartQueryFactory.eqCourseId(course, courseId)
                ).fetchFirst();

//        Integer queryResult = queryFactory.selectOne()
//                .from(courseRegistrationCart)
//                .join(user).on(user.id.eq(courseRegistrationCart.user.id))
//                .join(course).on(course.id.eq(courseRegistrationCart.course.id))
//                .where(user.id.eq(userId),
//                        course.id.eq(courseId))
//                .fetchFirst();

        return queryResult != null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseRegistrationCart> getCourseRegistrationCartByUserIdAndCourseId(Long userId, Long courseId) {
        CourseRegistrationCart queryResult = queryFactory.select(courseRegistrationCart)
                .from(courseRegistrationCart)
                .join(user).on(user.id.eq(courseRegistrationCart.user.id))
                .join(course).on(course.id.eq(courseRegistrationCart.course.id))
                .where(user.id.eq(userId),
                        course.id.eq(courseId))
                .fetchFirst();

        return Optional.ofNullable(queryResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseRegistrationCartResultDto> getCourseRegistrationCartResultDtosByUserId(Long userId) {
        List<CourseRegistrationCartResultDto> queryResults = queryFactory.select(new QCourseRegistrationCartResultDto(
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
                .from(courseRegistrationCart) // t1
                .join(user).on(user.id.eq(courseRegistrationCart.user.id))
                .join(course).on(course.id.eq(courseRegistrationCart.course.id))
                .join(courseGroup).on(courseGroup.id.eq(course.courseGroup.id))
                .join(college).on(college.id.eq(courseGroup.college.id))
                .leftJoin(collegeDivision).on(collegeDivision.id.eq(courseGroup.collegeDivision.id))
                .leftJoin(collegeDepartment).on(collegeDepartment.id.eq(courseGroup.collegeDepartment.id))
                .where(courseRegistrationCart.user.id.eq(userId))
                .orderBy(courseRegistrationCart.course.id.asc())
                .fetch();

        return queryResults;
    }
}