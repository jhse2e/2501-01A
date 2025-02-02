package app.sgsc.global.configuration.security;

import app.sgsc.global.configuration.exception.ApiException;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import app.sgsc.global.configuration.security.login.UserLoginDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {
    public static Long getUserId() {
        return getUserDetails().getId();
    }

    public static String getUserNumber() {
        return getUserDetails().getNumber();
    }

    public static UserLoginDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw ApiException.of401(ApiExceptionType.USER_AUTHENTICATION_NOT_VALID);
        }

        return (UserLoginDetails) authentication.getPrincipal();
    }
}