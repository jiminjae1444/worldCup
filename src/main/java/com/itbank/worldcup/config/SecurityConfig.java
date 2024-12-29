package com.itbank.worldcup.config;

import com.itbank.worldcup.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // HttpSecurity 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/user/login", "/user/join").permitAll() // 로그인, 회원가입 페이지는 누구나 접근 가능
                                .requestMatchers("/users/check","/users/joins").permitAll() // /users/check 엔드포인트는 누구나 접근 가능
                                .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN") // ADMIN 권한이 있는 사용자만 접근 가능
                                .requestMatchers("/user/**").authenticated() // 인증된 사용자만 접근 가능
                                .anyRequest().authenticated() // 나머지 모든 요청은 인증된 사용자만 접근 가능
                )
                // 폼 로그인 설정
                .formLogin(form -> form
                        .loginPage("/user/login") // 로그인 페이지 URL
                        .loginProcessingUrl("/login") // 로그인 요청을 처리할 URL
                        .permitAll() // 로그인 페이지는 누구나 접근 가능
                        .defaultSuccessUrl("/", true) // 로그인 성공 시 /home으로 리디렉션
                        .failureUrl("/user/login?error=true") // 로그인 실패 시 오류 메시지와 함께 리디렉션
                        .failureHandler(new SimpleUrlAuthenticationFailureHandler("/user/login?error=true")) // 로그인 실패 시 처리할 핸들러 (오류 메시지)
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 처리 URL
                        .logoutSuccessUrl("/user/login?logout=true") // 로그아웃 후 리디렉션할 URL
                        .invalidateHttpSession(true) // 세션 무효화
                        .clearAuthentication(true) // 인증 정보 지우기
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}
