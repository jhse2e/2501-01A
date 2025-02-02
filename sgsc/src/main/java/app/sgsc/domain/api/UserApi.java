package app.sgsc.domain.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserApi {

    @Operation(summary = "사용자 조회", description = "사용자를 조회한다.")
    ResponseEntity<?> getUser(
        // ...
    );

    @Operation(summary = "사용자 목록 조회", description = "사용자 목록을 조회한다.")
    ResponseEntity<?> getUsers(
        // ...
    );

    /**
     * user.csv 파일을 사용해서 사용자를 등록한다.
     */
    @Operation(summary = "사용자 목록 생성", description = "사용자 목록을 생성한다.")
    ResponseEntity<?> createUsers(
        MultipartFile userFile
    );
}