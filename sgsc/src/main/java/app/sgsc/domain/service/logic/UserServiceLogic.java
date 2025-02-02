package app.sgsc.domain.service.logic;

import app.sgsc.domain.db.rds.repository.query.UserRepository;
import app.sgsc.domain.dto.response.UserResultDto;
import app.sgsc.domain.service.UserService;
import app.sgsc.domain.service.logic.handler.UserFileHandler;
import app.sgsc.domain.service.logic.helper.UserPasswordHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceLogic implements UserService {
    private final UserFileHandler userFileHandler;
    private final UserPasswordHelper userPasswordHelper;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserResultDto getUser(Long userId) {
        return userRepository.getUserDtoByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResultDto> getUsers() {
        return userRepository.getUserDtosBy();
    }

    @Override
    @Transactional
    public void createUsers(MultipartFile userFile) {
        userFileHandler.execute(userFile, userPasswordHelper);
    }
}