package com.planify.main.api.member.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.planify.main.api.member.value.Gender;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    private Long memberNo;

    @Column(name = "member_id", nullable = false, unique = true)
    private String memberId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING) // Enum 값
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Column(name = "mod_dt")
    private LocalDateTime modDt;

    @Builder
    public Member(String memberId, String password, String nickName, String name, Gender gender, LocalDateTime regDt, LocalDateTime modDt) {
        this.memberId = memberId;
        this.password = password;
        this.nickName = nickName;
        this.name = name;
        this.gender = gender;
        this.regDt = regDt;
        this.modDt = modDt;
    }
}