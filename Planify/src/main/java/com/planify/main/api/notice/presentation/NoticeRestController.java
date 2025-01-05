package com.planify.main.api.notice.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planify.main.api.notice.application.NoticeService;
import com.planify.main.api.notice.application.dto.NoticeDTO;
import com.planify.main.common.ApiResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoticeRestController {

    private final NoticeService noticeService;

    // 공지사항 목록 조회 (DTO로 변환하여 반환)
    @GetMapping("/notices")
    public ApiResult<List<NoticeDTO>> getAllNotices() {
        List<NoticeDTO> notices = noticeService.getAllNotices();
        return ApiResult.success(notices);
    }

    // 공지사항 생성
    @PostMapping("/notices")
    public ApiResult<NoticeDTO> createNotice(@RequestBody NoticeDTO noticeDTO) {
        NoticeDTO createdNotice = noticeService.createNotice(noticeDTO);
        return ApiResult.success("공지사항이 생성되었습니다.", createdNotice);
    }

    // 공지사항 수정
    @PutMapping("/notices/{noticeId}")
    public ApiResult<NoticeDTO> updateNotice(@PathVariable Long noticeId, @RequestBody NoticeDTO updatedNoticeDTO) {
        NoticeDTO updatedNotice = noticeService.updateNotice(noticeId, updatedNoticeDTO);
        return ApiResult.success("공지사항이 수정되었습니다.", updatedNotice);
    }

    // 공지사항 조회
    @GetMapping("/notices/{noticeId}")
    public ApiResult<NoticeDTO> getNoticeById(@PathVariable Long noticeId) {
        NoticeDTO notice = noticeService.getNoticeById(noticeId);
        return ApiResult.success(notice);
    }

    // 공지사항 삭제
    @DeleteMapping("/notices/{noticeId}")
    public ApiResult<Void> deleteNoticeById(@PathVariable Long noticeId) {
        noticeService.deleteNoticeById(noticeId);
        return ApiResult.success("공지사항이 삭제되었습니다.", null);
    }
}
