package com.planify.main.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.planify.main.api.member.domain.Member;

/**
 * 인증된 사용자 정보를 제공하는 유틸
 * 현재 SecurityContext에 저장된 인증 정보를 기반으로 Member 객체 리턴
 */
public class AuthenticatedUserUtil {

    /**
     * 현재 인증된 사용자의 Member 객체 반환
     * 인증되지 않은 경우 null 반환
     */
    public static Member getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 객체가 없거나 익명 사용자 처리
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        // CustomUserDetails에서 Member 추출
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getMember();
        }

        return null; // 인증되지 않은 경우
    }
}
