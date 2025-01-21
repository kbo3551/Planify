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

    @PostMapping
    public ApiResult<?> login(@RequestBody Login login) {
        Member member = loginService.findByMemberId(login.getMemberId());

        if (member == null) {
            return ApiResult.failure(HttpStatus.BAD_REQUEST, "User not found");
        }

        if (member.isSocialLogin()) {
            return ApiResult.failure(HttpStatus.BAD_REQUEST, "소셜 로그인 사용자는 해당 소셜 계정으로 로그인해주세요.");
        }

        boolean isAuthenticated = loginService.authenticate(login.getMemberId(), login.getPassword());

        if (!isAuthenticated) {
            return ApiResult.failure(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 잘못되었습니다.");
        }

        CustomUserDetails userDetails = new CustomUserDetails(member);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        return ApiResult.success("로그인 성공", Map.of("redirectUrl", "/main"));
    }
}
