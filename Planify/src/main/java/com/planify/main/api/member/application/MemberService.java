package com.planify.main.api.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planify.main.api.member.application.dto.MemberDTO;
import com.planify.main.api.member.domain.Member;
import com.planify.main.api.member.infrastructure.MemberRepository;
import com.planify.main.config.security.AuthenticatedUserUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    
    public MemberDTO getMemberInfo() {

    	Member authenticatedUser = AuthenticatedUserUtil.getAuthenticatedUser();

        if (authenticatedUser == null) {
            throw new IllegalArgumentException("No authenticated user found");
        }

        Member member = memberRepository.findById(authenticatedUser.getMemberNo())
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + authenticatedUser.getMemberNo()));

        boolean isSocialLogin = member.isSocialLogin();

        return MemberDTO.builder()
                .memberNo(member.getMemberNo())
                .memberId(member.getMemberId())
                .password(null)
                .nickName(member.getNickName())
                .name(member.getName())
                .gender(member.getGender()) // M/W TO MALE/FEMALE
                .socialLogin(member.isSocialLogin()) // 소셜 로그인 여부 추가
                .socialProvider(member.isSocialLogin() ? member.getSocialProvider() : null) // 소셜 제공자 추가
                .build();
    }

    @Transactional
    public void updateMemberInfo(MemberDTO memberDTO) {
    	Member authenticatedUser = AuthenticatedUserUtil.getAuthenticatedUser();
        Member member = memberRepository.findById(authenticatedUser.getMemberNo())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        member.updateInfo(memberDTO.getNickName(), memberDTO.getName(), memberDTO.toGender());

        if (memberDTO.getPassword() != null && !memberDTO.getPassword().isEmpty()) {
            member.updatePassword(passwordEncoder.encode(memberDTO.getPassword()));
        }
    }
}
