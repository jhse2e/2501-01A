package app.sgsc.domain.service;

import app.sgsc.domain.dto.response.UserResultDto;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface UserService {

    /**
     * 사용자를 조회한다.
     */
    UserResultDto getUser(
        Long userId
    );

    /**
     * 사용자 목록을 조회한다.
     */
    List<UserResultDto> getUsers(
        // ...
    );

    /**
     * 사용자 목록을 생성한다.
     */
    void createUsers(
        MultipartFile userFile
    );
}