package com.planify.main.api.notice.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planify.main.api.member.domain.Member;
import com.planify.main.api.member.infrastructure.MemberRepository;
import com.planify.main.api.notice.application.dto.NoticeDTO;
import com.planify.main.api.notice.domain.Notice;
import com.planify.main.api.notice.infrastructure.NoticeRepository;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
/**
 * 공지사항 Service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    public List<NoticeDTO> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(NoticeDTO::ofEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public NoticeDTO createNotice(NoticeDTO noticeDTO) {
        Member member = memberRepository.findById(noticeDTO.getMemberNo())
            .orElseThrow(() -> new IllegalArgumentException("Invalid memberNo: " + noticeDTO.getMemberNo()));

        Notice notice = noticeDTO.toEntity(member);
        Notice savedNotice = noticeRepository.save(notice);

        return NoticeDTO.ofEntity(savedNotice);
    }

    @Transactional
    public NoticeDTO updateNotice(Long noticeId, NoticeDTO noticeDTO) {
        Notice notice = noticeRepository.findById(noticeId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid NoticeId: " + noticeId));

        Member member = memberRepository.findById(noticeDTO.getMemberNo())
            .orElseThrow(() -> new IllegalArgumentException("Invalid memberNo: " + noticeDTO.getMemberNo()));

        notice.update(noticeDTO.getTitle(), noticeDTO.getContent(), member);
        return NoticeDTO.ofEntity(notice);
    }

    public NoticeDTO getNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId)
            .map(NoticeDTO::ofEntity)
            .orElseThrow(() -> new IllegalArgumentException("Notice not found: " + noticeId));
    }

    public void deleteNoticeById(Long noticeId) {
        if (!noticeRepository.existsById(noticeId)) {
            throw new IllegalArgumentException("Notice not found: " + noticeId);
        }
        noticeRepository.deleteById(noticeId);
    }
}