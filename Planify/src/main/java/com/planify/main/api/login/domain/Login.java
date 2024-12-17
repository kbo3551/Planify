package com.planify.main.api.login.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Login {
    private String memberId; 
    private String password;
    
    @Builder
    public Login(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }
}