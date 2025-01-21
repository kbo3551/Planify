package com.planify.main.api.member.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.planify.main.api.member.value.Gender;
import com.planify.main.api.roles.domain.Roles;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Column(name = "mod_dt")
    private LocalDateTime modDt;

    @Column(name = "social_login", nullable = false)
    private boolean socialLogin = false;

    @Column(name = "social_provider")
    private String socialProvider;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_roles",
            joinColumns = @JoinColumn(name = "member_no"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles = new HashSet<>();

    @Builder
    public Member(Long memberNo, String memberId, String password, String nickName, String name, Gender gender, LocalDateTime regDt, LocalDateTime modDt, boolean socialLogin, String socialProvider) {
    	  this.memberNo = memberNo;
        this.memberId = memberId;
        this.password = password;
        this.nickName = nickName;
        this.name = name;
        this.gender = gender;
        this.regDt = regDt;
        this.modDt = modDt;
        this.socialLogin = socialLogin;
        this.socialProvider = socialProvider;
    }

    public void addRole(Roles role) {
        this.roles.add(role);
    }

    public void updateInfo(String nickName, String name, Gender gender) {
        this.nickName = nickName;
        this.name = name;
        this.gender = gender;
        this.modDt = LocalDateTime.now();
    }

    public void updatePassword(String password) {
        this.password = password;
        this.modDt = LocalDateTime.now();
    }
    
    public void updateMemberId(String memberId) {
        this.memberId = memberId;
    }
}
