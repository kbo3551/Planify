package com.planify.main.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
/**
 * Spring Security의 AuthenticationEntryPoint 구현체
 * 인증되지 않은 사용자 요청을 처리하며, 요청 유형(AJAX/일반)에 따라 응답 또는 리다이렉트 반환
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        // AJAX 요청인 경우 JSON 응답
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
        } else {
            // 일반 요청은 로그인 페이지로 리다이렉트
            response.sendRedirect("/login");
        }
    }
}