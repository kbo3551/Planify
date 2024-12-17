package com.planify.main.api.login.application.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.planify.main.api.member.value.Gender;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddMemberDTO {
    private String memberId;
    private String password;
    private String nickName;
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Builder
    public AddMemberDTO(String memberId, String password, String nickName, String name, Gender gender) {
        this.memberId = memberId;
        this.password = password;
        this.nickName = nickName;
        this.name = name;
        this.gender = gender;
    }
}