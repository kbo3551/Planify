package com.planify.main.config.security;

import com.planify.main.api.member.domain.Member;
import com.planify.main.api.member.infrastructure.MemberRepository;
import com.planify.main.api.member.value.Gender;
import com.planify.main.api.roles.domain.Roles;
import com.planify.main.api.roles.infrastructure.RolesRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final RolesRepository rolesRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository, RolesRepository rolesRepository) {
        this.memberRepository = memberRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String kakaoId = String.valueOf(attributes.get("id"));
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        String nickname = (String) properties.get("nickname");

        Member member = memberRepository.findByMemberId("kakao_" + kakaoId).orElseGet(() -> {
            Member newMember = Member.builder()
                    .memberId("kakao_" + kakaoId)
                    .password("") // 소셜 로그인은 비밀번호 X
                    .nickName(nickname)
                    .name(nickname)
                    .gender(Gender.U) // UNKNOWN으로 설정
                    .socialLogin(true)
                    .socialProvider("KAKAO")
                    .regDt(LocalDateTime.now())
                    .modDt(LocalDateTime.now())
                    .build();

            // ROLE_USER 권한 추가
            Roles userRole = rolesRepository.findByRoleName("ROLE_USER")
                    .orElseThrow(() -> new IllegalArgumentException("ROLE_USER가 존재하지 않습니다."));
            newMember.addRole(userRole);

            memberRepository.save(newMember);
            return newMember;
        });

        // CustomUserDetails 생성
        CustomUserDetails userDetails = new CustomUserDetails(member, attributes);

        // 인증 객체 생성
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        // SecurityContext에 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 반환: OAuth2User 구현체
        return userDetails;
    }
}