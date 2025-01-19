package com.planify.main.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.planify.main.api.member.domain.Member;
import com.planify.main.api.member.infrastructure.MemberRepository;
/**
 * Spring Security UserDetailsService 구현체
 * 사용자 인증을 위해 DB에서 사용자 정보를 로드함
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    // 생성자
    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 사용자 이름(회원 ID)을 기반으로 사용자 정보를 조회
     * AuthenticationManager에서 인증을 수행할 때 호출됨
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with memberId: " + username));
        return new CustomUserDetails(member);
    }
}