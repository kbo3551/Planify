package com.planify.main.api.login.application;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planify.main.api.member.domain.Member;
import com.planify.main.api.member.infrastructure.MemberRepository;
import com.planify.main.api.member.value.Gender;
import com.planify.main.api.roles.domain.Roles;
import com.planify.main.api.roles.infrastructure.RolesRepository;

@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,RolesRepository rolesRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
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

    @Transactional
    public void register(String memberId, String password, String nickName, String name, Gender gender, boolean isAdmin) {
        // 비밀번호 유효성 검사
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // 이미 존재하는 ID 검사
        memberRepository.findByMemberId(memberId).ifPresent(member -> {
            throw new IllegalArgumentException("사용중인 ID입니다.");
        });

        // 현재 시간 저장
        LocalDateTime now = LocalDateTime.now();

        // 새로운 회원 객체 생성
        Member newMember = Member.builder()
                .memberId(memberId)
                .password(passwordEncoder.encode(password))
                .nickName(nickName)
                .name(name)
                .gender(gender)
                .regDt(now)
                .modDt(now)
                .build();

        // 회원 저장
        memberRepository.save(newMember);

        // 기본적으로 ROLE_USER 권한 부여
        Roles userRole = rolesRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new IllegalArgumentException("ROLE_USER가 존재하지 않습니다."));
        newMember.addRole(userRole);

        // 관리자인 경우 ROLE_ADMIN 권한 부여
        if (isAdmin) {
            Roles adminRole = rolesRepository.findByRoleName("ROLE_ADMIN")
                    .orElseThrow(() -> new IllegalArgumentException("ROLE_ADMIN이 존재하지 않습니다."));
            newMember.addRole(adminRole);
        }
    }
}