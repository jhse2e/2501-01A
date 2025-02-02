package app.sgsc.domain.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface CollegeApi {

    @Operation(summary = "대학 목록 조회", description = "대학 목록을 조회한다.")
    ResponseEntity<?> getColleges(
        // ...
    );

    @Operation(summary = "대학 학부 목록 조회", description = "대학에 속한 학부 목록을 조회한다.")
    ResponseEntity<?> getCollegeDivisions(
        Long collegeId
    );

    @Operation(summary = "대학 학과 목록 조회", description = "대학에 속한 학과 목록을 조회한다.")
    ResponseEntity<?> getCollegeDepartments(
        Long collegeId
    );
}