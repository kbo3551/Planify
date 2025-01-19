package com.planify.main.api.login.application.dto;

import com.planify.main.api.member.value.Gender;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddUserDTO {
    private String memberId;
    private String password;
    private String nickName;
    private String name;
//    @Enumerated(EnumType.STRING)
    private Gender gender;
    private boolean isAdmin;

    @Builder
    public AddUserDTO(String memberId, String password, String nickName, String name, Gender gender, boolean isAdmin) {
        this.memberId = memberId;
        this.password = password;
        this.nickName = nickName;
        this.name = name;
        this.gender = gender;
        this.isAdmin = isAdmin;
    }
}