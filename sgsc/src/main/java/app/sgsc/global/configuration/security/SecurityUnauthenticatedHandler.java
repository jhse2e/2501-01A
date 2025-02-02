package app.sgsc.global.configuration.security;

import app.sgsc.global.common.api.ApiResponse;
import app.sgsc.global.configuration.exception.type.ApiExceptionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class SecurityUnauthenticatedHandler implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception
    ) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.create(ApiExceptionType.USER_AUTHENTICATION_NOT_VALID)));
    }
}