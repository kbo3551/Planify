package com.planify.main.api.login.presentation;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planify.main.api.login.application.LoginService;
import com.planify.main.api.login.application.dto.AddMemberDTO;
import com.planify.main.api.login.domain.Login;

@RestController
@RequestMapping("/api/login")
public class LoginRestController {

    private final LoginService loginService;

    public LoginRestController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AddMemberDTO addMemberDTO) {
        try {
            loginService.register(
                    addMemberDTO.getMemberId(),
                    addMemberDTO.getPassword(),
                    addMemberDTO.getNickName(),
                    addMemberDTO.getName(),
                    addMemberDTO.getGender()
            );
            return ResponseEntity.ok(Map.of("message", "회원가입 성공"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody Login login) {
        boolean isAuthenticated = loginService.authenticate(login.getMemberId(), login.getPassword());

        if (isAuthenticated) {
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    login.getMemberId(), null, null
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }
}