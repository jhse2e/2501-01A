package app.sgsc.domain.api.logic;

import app.sgsc.domain.api.UserApi;
import app.sgsc.domain.dto.response.UserResultDto;
import app.sgsc.domain.service.UserService;
import app.sgsc.global.common.api.ApiResponse;
import app.sgsc.global.configuration.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiLogic implements UserApi {
    private final UserService userService;

    @Override
    @GetMapping(value = {"/api/users/me"})
    public ResponseEntity<?> getUser(
        // ...
    ) {
        UserResultDto apiResult = userService.getUser(SecurityUtils.getUserId());

        return ApiResponse.ok(apiResult);
    }

    @Override
    @GetMapping(value = {"/api/users"})
    public ResponseEntity<?> getUsers(
        // ...
    ) {
        List<UserResultDto> apiResult = userService.getUsers();

        return ApiResponse.ok(apiResult);
    }

    @Override
    @PostMapping(value = {"/api/users"})
    public ResponseEntity<?> createUsers(
        @RequestPart(name = "userFile", required = true) MultipartFile userFile
    ) {
        userService.createUsers(userFile);

        return ApiResponse.ok();
    }
}