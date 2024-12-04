package com.itbank.worldcup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

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
                                .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN") // ADMIN 권한이 있는 사용자만 접근 가능
                                .requestMatchers("/user/**").authenticated() // 인증된 사용자만 접근 가능
                                .anyRequest().authenticated() // 나머지 모든 요청은 인증된 사용자만 접근 가능
                )
                // 폼 로그인 설정
                .formLogin(form -> form
                        .loginPage("/login") // 커스텀 로그인 페이지
                        .permitAll() // 로그인 페이지는 누구나 접근 가능
                )
                // 로그아웃 설정
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

}
