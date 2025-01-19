package com.planify.main.api.login.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Login {
    private String memberId; 
    private String password;
    
    @JsonCreator
    @Builder
    public Login(@JsonProperty("memberId") String memberId, 
                 @JsonProperty("password") String password) {
        this.memberId = memberId;
        this.password = password;
    }

}