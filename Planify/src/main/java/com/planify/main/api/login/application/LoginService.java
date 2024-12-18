package com.planify.main.api.login.application;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.planify.main.api.member.domain.Member;
import com.planify.main.api.member.infrastructure.MemberRepository;
import com.planify.main.api.member.value.Gender;

@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 로그인 기능
    public boolean authenticate(String memberId, String rawPassword) {
        return memberRepository.findByMemberId(memberId)
                .map(member -> passwordEncoder.matches(rawPassword, member.getPassword()))
                .orElse(false);
    }

    public Member findByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));
    }

    public void register(String memberId, String password, String nickName, String name, Gender gender) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        memberRepository.findByMemberId(memberId).ifPresent(member -> {
            throw new IllegalArgumentException("사용중인 ID입니다.");
        });

        LocalDateTime now = LocalDateTime.now();
        Member newMember = Member.builder()
                .memberId(memberId)
                .password(passwordEncoder.encode(password))
                .nickName(nickName)
                .name(name)
                .gender(gender)
                .regDt(now)
                .modDt(now)
                .build();

        memberRepository.save(newMember);
    }
}