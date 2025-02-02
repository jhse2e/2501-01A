package app.sgsc.domain.db.rds.repository.querydsl;

import app.sgsc.domain.db.rds.entity.User;
import app.sgsc.domain.dto.response.UserResultDto;
import java.util.List;
import java.util.Optional;

public interface UserRepositorySupport {

    /**
     * 사용자 중복을 확인한다.
     */
    Boolean hasUserByUserNumber(
        String userNumber
    );

    /**
     * 사용자를 조회한다.
     */
    Optional<User> getUserByUserId(
        Long userId
    );

    /**
     * 사용자를 조회한다.
     */
    Optional<User> getUserByUserNumber(
        String userNumber
    );

    /**
     * 사용자를 조회한다.
     */
    UserResultDto getUserDtoByUserId(
        Long userId
    );

    /**
     * 사용자 목록을 조회한다.
     */
    List<UserResultDto> getUserDtosBy(
        // ...
    );
}