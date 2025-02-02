package app.sgsc.global.configuration.security;

import app.sgsc.global.configuration.security.jwt.JwtAuthenticationService;
import app.sgsc.global.configuration.security.jwt.filter.JwtAuthenticationFilter;
import app.sgsc.global.configuration.security.jwt.filter.JwtAuthenticationProcessingFilter;
import app.sgsc.global.configuration.security.login.handler.UserLoginFailureHandler;
import app.sgsc.global.configuration.security.login.handler.UserLoginSuccessHandler;
import app.sgsc.global.configuration.security.logout.handler.UserLogoutSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsUtils;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final ObjectMapper objectMapper;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final AuthenticationConfiguration authenticationConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder(4); // 기본값 10 (= -1)
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfig) throws Exception {
        return authenticationConfig.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtAuthenticationService);
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() throws Exception {
        JwtAuthenticationProcessingFilter filter = new JwtAuthenticationProcessingFilter(objectMapper);

        filter.setAuthenticationManager(authenticationManager(authenticationConfig));
        filter.setAuthenticationSuccessHandler(userLoginSuccessHandler());
        filter.setAuthenticationFailureHandler(userLoginFailureHandler());

        return filter;
    }

    @Bean
    public UserLoginSuccessHandler userLoginSuccessHandler() {
        return new UserLoginSuccessHandler(objectMapper, jwtAuthenticationService);
    }

    @Bean
    public UserLoginFailureHandler userLoginFailureHandler() {
        return new UserLoginFailureHandler(objectMapper);
    }

    @Bean
    public UserLogoutSuccessHandler userLogoutSuccessHandler() {
        return new UserLogoutSuccessHandler(objectMapper);
    }

    @Bean
    public SecurityUnauthorizedHandler securityUnauthorizedHandler() {
        return new SecurityUnauthorizedHandler(objectMapper);
    }

    @Bean
    public SecurityUnauthenticatedHandler securityUnauthenticatedHandler() {
        return new SecurityUnauthenticatedHandler(objectMapper);
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().requestMatchers("/favicon.ico", "/image/**", "/script/**", "/style/**");
        // return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        http.sessionManagement(e -> e
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(e -> e
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .requestMatchers("/**").permitAll()
                // .requestMatchers("/").permitAll()
                // .requestMatchers("/course", "/course/list", "/course/registration", "/course/registration/cart").authenticated()
                // .requestMatchers("/api/users/login").permitAll()
                // .requestMatchers("/api/users/**", "/api/colleges/**", "/api/courses/**").authenticated()
                // .requestMatchers("/ws/**", "/ws-sub/**").permitAll()
                // .requestMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
        );

        http.exceptionHandling(e -> e
                .authenticationEntryPoint(securityUnauthenticatedHandler())
                .accessDeniedHandler(securityUnauthorizedHandler()));

        http.addFilterAfter(jwtAuthenticationProcessingFilter(), LogoutFilter.class); // 1. LogoutFilter -> 2. JwtAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter(), JwtAuthenticationProcessingFilter.class); // 2. JwtAuthenticationFilter -> 3. JwtAuthenticationProcessingFilter

        return http.build();
    }
}