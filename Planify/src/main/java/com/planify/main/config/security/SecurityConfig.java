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

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable()) // CSRF는 필요 시 활성화
	        .authorizeHttpRequests(auth -> auth
						.antMatchers(/* "/main", */"/","/login", "/resources/**", "/register", "/api/login/**", "/h2-console/**", "/public/**").permitAll()
	            .anyRequest().authenticated()
	        )
	        .logout(logout -> logout
	            .logoutSuccessUrl("/login")
	            .permitAll()
	        )
	        .exceptionHandling(exception -> exception
	            .authenticationEntryPoint((request, response, authException) -> {
	                response.setContentType("application/json");
	                response.setStatus(401);
	                response.getWriter().write("{\"error\": \"Unauthorized\"}");
	            })
	        );

	    return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}