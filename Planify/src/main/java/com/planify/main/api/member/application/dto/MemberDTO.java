package com.planify.main.api.member.application.dto;

import com.planify.main.api.member.domain.Member;
import com.planify.main.api.member.value.Gender;
import com.planify.main.api.roles.domain.Roles;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MemberDTO {
    private Long memberNo;
    private String memberId;
    private String password;
    private String nickName;
    private String name;

    private String gender; // MALE or FEMALE
    private boolean socialLogin; // 소셜 로그인 여부
    private String socialProvider; // 소셜 로그인 제공자
    private Set<String> roles;  // 권한 이름 목록

    @Builder
    public MemberDTO(Long memberNo, String memberId, String password, String nickName, String name, Gender gender, Set<String> roles, boolean socialLogin, String socialProvider) {
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.password = password;
        this.nickName = nickName;
        this.name = name;
        this.gender = gender.getDisplayName(); // MALE or FEMALE
        this.socialLogin = socialLogin;
        this.socialProvider = socialProvider;
        this.roles = roles;
    }

    public Gender toGender() {
        return Gender.fromDisplayName(this.gender);
    }

    // Entity -> DTO 변환
    public static MemberDTO ofEntity(Member member) {
        return MemberDTO.builder()
                .memberNo(member.getMemberNo())
                .memberId(member.getMemberId())
                .password(member.getPassword())
                .nickName(member.getNickName())
                .name(member.getName())
                .gender(member.getGender())
                .roles(member.getRoles()
                        .stream()
                        .map(Roles::getRoleName)
                        .collect(Collectors.toSet()))
                .build();
    }

    // DTO -> Entity 변환
    public Member toEntity() {
        Member member = Member.builder()
                .memberNo(this.memberNo)
                .memberId(this.memberId)
                .password(this.password)
                .nickName(this.nickName)
                .name(this.name)
                .gender(this.toGender())
                .build();

        // 권한 추가
        if (this.roles != null) {
            Set<Roles> rolesSet = this.roles.stream()
                    .map(roleName -> Roles.builder().roleName(roleName).build())
                    .collect(Collectors.toSet());
            member.getRoles().addAll(rolesSet);
        }

        return member;
    }

    // 권한 추가
    public void addRole(String roleName) {
        if (this.roles != null) {
            this.roles.add(roleName);
        }
    }

    // 권한 제거
    public void removeRole(String roleName) {
        if (this.roles != null) {
            this.roles.remove(roleName);
        }
    }
}
