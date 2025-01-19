package com.planify.main.api.login.presentation;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planify.main.api.login.application.LoginService;
import com.planify.main.api.login.application.dto.AddUserDTO;
import com.planify.main.api.login.domain.Login;
import com.planify.main.api.member.domain.Member;
import com.planify.main.common.ApiResult;
import com.planify.main.config.security.CustomUserDetails;

@RestController
@RequestMapping("/api/login")
public class LoginRestController {

    private final LoginService loginService;

    public LoginRestController(LoginService loginService) {
        this.loginService = loginService;
    }

    // 회원가입
    @PostMapping("/register")
    public ApiResult<?> register(@RequestBody AddUserDTO addUserDTO) {
        try {
            loginService.register(
                    addUserDTO.getMemberId(),
                    addUserDTO.getPassword(),
                    addUserDTO.getNickName(),
                    addUserDTO.getName(),
                    addUserDTO.getGender(),
                    addUserDTO.isAdmin()
            );
            return ApiResult.success("회원가입 성공", null);
        } catch (IllegalArgumentException e) {
            return ApiResult.failure(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // 로그인
    @PostMapping
    public ApiResult<?> login(@RequestBody Login login, HttpSession session) {
    	// 사용자 인증 여부 확인
        boolean isAuthenticated = loginService.authenticate(login.getMemberId(), login.getPassword());

        if (isAuthenticated) {
        	// 회원 정보를 조회
            Member member = loginService.findByMemberId(login.getMemberId());
            // CustomUserDetails로 Member 감싸기
            CustomUserDetails userDetails = new CustomUserDetails(member);

            // Spring Security의 Authentication 객체 생성
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            // Spring Security의 SecurityContext에 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            // 세션에 Spring Security의 컨텍스트(SecurityContext)를 저장
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return ApiResult.success("로그인 성공", Map.of("redirectUrl", "/main"));
        } else {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }
}
