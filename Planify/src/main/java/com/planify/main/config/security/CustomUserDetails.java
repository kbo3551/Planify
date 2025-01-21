package com.planify.main.config.security;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.planify.main.api.member.domain.Member;

/**
 * Spring Security의 UserDetails 및 OAuth2User 구현체
 * Member 객체를 기반으로 인증 데이터 관리
 * Spring Security에서 인증 및 권한 관리 활용
 */
public class CustomUserDetails implements UserDetails, OAuth2User {

    private static final long serialVersionUID = 8951392406763639015L;
    private final Member member;
    private Map<String, Object> attributes; // OAuth2 속성

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    public CustomUserDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    public Member getMember() {
        return member;
    }

    // OAuth2User: 속성 반환
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // OAuth2User: 이름 반환
    @Override
    public String getName() {
        return member.getMemberId(); // OAuth2User의 기본 이름 속성
    }

    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return member.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getMemberId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
