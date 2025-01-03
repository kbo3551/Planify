package com.planify.main.api.notice.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.planify.main.api.member.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "notice")
public class Notice {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @Column(updatable = false)
    private LocalDateTime regDt = LocalDateTime.now();

    private LocalDateTime modDt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_no", referencedColumnName = "member_no", nullable = true)
    private Member member;
    
    @PreUpdate
    public void preUpdate() {
        this.modDt = LocalDateTime.now();
    }
    
    @Builder
	public Notice(String title, String content, Member member) {
		this.title = title;
		this.content = content;
		this.member = member;
	}
    
    public void update(String title, String content, Member member) {
		this.title = title;
		this.content = content;
		this.modDt = LocalDateTime.now();
	}
}
