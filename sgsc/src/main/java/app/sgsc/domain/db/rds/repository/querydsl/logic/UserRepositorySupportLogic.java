package app.sgsc.domain.db.rds.repository.querydsl.logic;

import app.sgsc.domain.db.rds.entity.QUser;
import app.sgsc.domain.db.rds.entity.User;
import app.sgsc.domain.db.rds.repository.querydsl.logic.factory.UserQueryFactory;
import app.sgsc.domain.db.rds.repository.querydsl.UserRepositorySupport;
import app.sgsc.domain.dto.response.QUserResultDto;
import app.sgsc.domain.dto.response.UserResultDto;
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
public class UserRepositorySupportLogic implements UserRepositorySupport {
    private final JPAQueryFactory queryFactory;
    private final QUser user = QUser.user;

    @Override
    @Transactional(readOnly = true)
    public Boolean hasUserByUserNumber(String userNumber) {
        Integer queryResult = queryFactory.selectOne()
                .from(user)
                .where(UserQueryFactory.eqUserNumber(user, userNumber))
                .fetchFirst();

        return queryResult != null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUserId(Long userId) {
        User queryResult = queryFactory.select(user)
                .from(user)
                .where(UserQueryFactory.eqUserId(user, userId))
                .fetchOne();

        return Optional.ofNullable(queryResult);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUserNumber(String userNumber) {
        User queryResult = queryFactory.select(user)
                .from(user)
                .where(UserQueryFactory.eqUserNumber(user, userNumber))
                .fetchOne();

        return Optional.ofNullable(queryResult);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResultDto getUserDtoByUserId(Long userId) {
        UserResultDto queryResult = queryFactory.select(new QUserResultDto(
                    user.id,
                    user.name,
                    user.number,
                    user.registrationCredit,
                    user.registrationCreditLeft,
                    user.registrationCreditLimit))
                .from(user)
                .where(UserQueryFactory.eqUserId(user, userId))
                .fetchOne();

        return queryResult;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResultDto> getUserDtosBy() {
        List<UserResultDto> queryResults = queryFactory.select(new QUserResultDto(
                    user.id,
                    user.name,
                    user.number,
                    user.registrationCredit,
                    user.registrationCreditLeft,
                    user.registrationCreditLimit))
                .from(user)
                .fetch();

        return queryResults;
    }
}