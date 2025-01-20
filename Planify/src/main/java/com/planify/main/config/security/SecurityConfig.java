package com.planify.main.config.security;

import com.planify.main.api.member.infrastructure.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                          CustomOAuth2UserService customOAuth2UserService) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 개발 환경에서만 비활성화
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // H2 콘솔 접근 허용
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/h2-console/**").permitAll() // H2 DB 콘솔 허용
                        .antMatchers("/", "/login", "/resources/**", "/register", "/api/login/**", "/public/**").permitAll() // 공개 경로
                        .antMatchers("/admin/**").hasRole("ADMIN") // 관리자 경로
                        .antMatchers("/main").authenticated() // 인증된 사용자만 접근 가능
                        .anyRequest().hasRole("USER") // 그 외 경로는 ROLE_USER 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // 사용자 정의 로그인 페이지
                        .defaultSuccessUrl("/main", true) // 로그인 성공 후 항상 /main으로 이동
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // 사용자 정보 처리 서비스
                        )
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login") // 로그아웃 성공 후 이동
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // 동시 세션 제한
                        .expiredUrl("/login?expired") // 세션 만료 시 이동 경로
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint) // 인증 실패 핸들링
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
