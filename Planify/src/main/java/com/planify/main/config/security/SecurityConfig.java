package com.planify.main.config.security;

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

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable()) // CSRF는 필요 시 활성화
	        .headers(headers -> headers
	                .frameOptions(frameOptions -> frameOptions.disable()) // X-Frame-Options 비활성화 -> 아이프레임 사용시 비활성화 ex)H2 console
	        )
	        .authorizeHttpRequests(auth -> auth
	                .antMatchers("/h2-console/**").permitAll() // H2 콘솔 접근 허용
	                .antMatchers("/", "/login", "/resources/**", "/register", "/api/login/**", "/public/**").permitAll()
	            .anyRequest().authenticated()
	        )
	        .logout(logout -> logout
	            .logoutSuccessUrl("/login")
	            .permitAll()
	        )
	        .sessionManagement(session -> session
	                .maximumSessions(1) // 세션 최대 1개
	                .expiredUrl("/login?expired") // 세션 만료 시 리다이렉트
	        )
	        .exceptionHandling(exception -> exception
	                .authenticationEntryPoint(customAuthenticationEntryPoint) // 커스텀 EntryPoint
	        );

	    return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}