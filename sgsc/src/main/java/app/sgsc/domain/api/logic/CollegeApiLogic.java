package app.sgsc.domain.api.logic;

import app.sgsc.domain.api.CollegeApi;
import app.sgsc.domain.dto.response.CollegeDepartmentResultDto;
import app.sgsc.domain.dto.response.CollegeDivisionResultDto;
import app.sgsc.domain.dto.response.CollegeResultDto;
import app.sgsc.domain.service.CollegeService;
import app.sgsc.global.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CollegeApiLogic implements CollegeApi {
    private final CollegeService collegeService;

    @Override
    @GetMapping(value = {"/api/colleges"})
    public ResponseEntity<?> getColleges(
        // ...
    ) {
        List<CollegeResultDto> apiResult = collegeService.getColleges();

        return ApiResponse.ok(apiResult);
    }

    @Override
    @GetMapping(value = {"/api/colleges/divisions"})
    public ResponseEntity<?> getCollegeDivisions(
        @RequestParam(name = "collegeId", required = true) Long collegeId
    ) {
        List<CollegeDivisionResultDto> apiResult = collegeService.getCollegeDivisions(collegeId);

        return ApiResponse.ok(apiResult);
    }

    @Override
    @GetMapping(value = {"/api/colleges/departments"})
    public ResponseEntity<?> getCollegeDepartments(
        @RequestParam(name = "collegeId", required = true) Long collegeId
    ) {
        List<CollegeDepartmentResultDto> apiResult = collegeService.getCollegeDepartments(collegeId);

        return ApiResponse.ok(apiResult);
    }
}