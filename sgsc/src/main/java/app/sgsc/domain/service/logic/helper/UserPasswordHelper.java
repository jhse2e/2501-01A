package app.sgsc.domain.service.logic.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserPasswordHelper {
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 비밀번호를 암호화한다.
     */
    public String encrypt(String userPassword) {
        return passwordEncoder.encode(userPassword);
    }

    /**
     * 사용자 비밀번호를 복호화한다.
     */
    public String decrypt(String userPassword) {
        return "";
    }
}