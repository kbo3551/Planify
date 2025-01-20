package com.planify.main.api.member.application.dto;

import com.planify.main.api.member.value.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public MemberDTO(Long memberNo, String memberId, String password, String nickName, String name, Gender gender, boolean socialLogin, String socialProvider) {
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.password = password;
        this.nickName = nickName;
        this.name = name;
        this.gender = gender.getDisplayName(); // MALE or FEMALE
        this.socialLogin = socialLogin;
        this.socialProvider = socialProvider;
    }

    public Gender toGender() {
        return Gender.fromDisplayName(this.gender); // MALE/FEMALE to M/W
    }
}
