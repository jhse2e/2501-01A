package app.sgsc.global.configuration.security.login;

import app.sgsc.domain.db.rds.entity.User;
import app.sgsc.domain.db.rds.repository.query.UserRepository;
import app.sgsc.global.configuration.exception.ApiException;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLoginDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userNumber) {
        User user = userRepository.getUserByUserNumber(userNumber).orElseThrow(() -> ApiException.of404(ApiExceptionType.USER_NOT_EXISTED));

        return UserLoginDetails.of(user);
    }
}