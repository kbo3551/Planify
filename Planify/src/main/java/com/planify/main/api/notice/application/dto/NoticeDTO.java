package com.planify.main.api.notice.application.dto;

import java.time.LocalDateTime;

import com.planify.main.api.member.domain.Member;
import com.planify.main.api.notice.domain.Notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeDTO {

	private Long noticeId;
	private String title;
	private String content;
	private LocalDateTime regDt;
	private LocalDateTime modDt;
	private Long memberNo;
	private String regName;

	@Builder
	public NoticeDTO(Long noticeId, String title, String content,
			LocalDateTime regDt, LocalDateTime modDt, Long memberNo, String regName) {
		this.noticeId = noticeId;
		this.title = title;
		this.content = content;
		this.regDt = regDt;
		this.modDt = modDt;
		this.memberNo = memberNo;
		this.regName = regName;
	}
	
	// Entity -> DTO 변환
	public static NoticeDTO ofEntity(Notice notice) {
		return NoticeDTO.builder()
			.noticeId(notice.getNoticeId())
			.title(notice.getTitle())
			.content(notice.getContent())
			.regDt(notice.getRegDt())
			.modDt(notice.getModDt())
			.memberNo(notice.getMember().getMemberNo() != null ? notice.getMember().getMemberNo() : null)
			.regName(notice.getMember() != null ? notice.getMember().getName() : "Unknown") // 작성자 이름 추가
			.build();
	}
	
	// DTO -> Entity 변환
	public Notice toEntity(Member member) {
		return Notice.builder()
            .title(this.title)
            .content(this.content)
            .member(member)
            .build();
    }

}