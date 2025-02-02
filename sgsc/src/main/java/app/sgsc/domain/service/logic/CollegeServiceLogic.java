package app.sgsc.domain.service.logic;

import app.sgsc.domain.db.rds.repository.query.CollegeDepartmentRepository;
import app.sgsc.domain.db.rds.repository.query.CollegeDivisionRepository;
import app.sgsc.domain.db.rds.repository.query.CollegeRepository;
import app.sgsc.domain.dto.response.CollegeDepartmentResultDto;
import app.sgsc.domain.dto.response.CollegeDivisionResultDto;
import app.sgsc.domain.dto.response.CollegeResultDto;
import app.sgsc.domain.service.CollegeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollegeServiceLogic implements CollegeService {
    private final CollegeRepository collegeRepository;
    private final CollegeDivisionRepository collegeDivisionRepository;
    private final CollegeDepartmentRepository collegeDepartmentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CollegeResultDto> getColleges() {
        return collegeRepository.getCollegeDtosBy();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CollegeDivisionResultDto> getCollegeDivisions(Long collegeId) {
        return collegeDivisionRepository.getCollegeDivisionResultDtosByCollegeId(collegeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CollegeDepartmentResultDto> getCollegeDepartments(Long collegeId) {
        return collegeDepartmentRepository.getCollegeDepartmentResultDtosByCollegeId(collegeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CollegeDepartmentResultDto> getCollegeDepartments(Long collegeId, Long collegeDivisionId) {
        return collegeDepartmentRepository.getCollegeDepartmentResultDtosByCollegeId(collegeId, collegeDivisionId);
    }
}