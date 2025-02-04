package com.planify.main.api.notice.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.planify.main.api.notice.application.NoticeService;
import com.planify.main.api.notice.application.dto.NoticeDTO;
import com.planify.main.common.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "공지사항 API", description = "공지사항을 관리하는 API")
public class NoticeRestController {

    private final NoticeService noticeService;

    // 공지사항 목록 조회 (DTO로 변환하여 반환)
    @GetMapping("/api/notices")
    @Operation(summary = "공지사항 목록 조회", description = "등록된 모든 공지사항을 조회합니다.")
    public ApiResult<List<NoticeDTO>> getAllNotices() {
        List<NoticeDTO> notices = noticeService.getAllNotices();
        return ApiResult.success(notices);
    }

    // 공지사항 생성
    @PostMapping("/admin/api/notices")
    @Operation(summary = "공지사항 등록", description = "공지사항을 등록합니다.")
    public ApiResult<NoticeDTO> createNotice(@RequestBody NoticeDTO noticeDTO) {
        NoticeDTO createdNotice = noticeService.createNotice(noticeDTO);
        return ApiResult.success("공지사항이 등록되었습니다.", createdNotice);
    }

    // 공지사항 수정
    @PutMapping("/admin/api/notices/{noticeId}")
    @Operation(summary = "공지사항 수정", description = "공지사항을 수정합니다.")
    public ApiResult<NoticeDTO> updateNotice(@PathVariable Long noticeId, @RequestBody NoticeDTO updatedNoticeDTO) {
        NoticeDTO updatedNotice = noticeService.updateNotice(noticeId, updatedNoticeDTO);
        return ApiResult.success("공지사항이 수정되었습니다.", updatedNotice);
    }

    // 공지사항 조회
    @GetMapping("/api/notices/{noticeId}")
    @Operation(summary = "공지사항 상세 조회", description = "특정 공지사항을 조회합니다.")
    public ApiResult<NoticeDTO> getNoticeById(@PathVariable Long noticeId) {
        NoticeDTO notice = noticeService.getNoticeById(noticeId);
        return ApiResult.success(notice);
    }

    // 공지사항 삭제
    @DeleteMapping("/admin/api/notices/{noticeId}")
    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    public ApiResult<Void> deleteNoticeById(@PathVariable Long noticeId) {
        noticeService.deleteNoticeById(noticeId);
        return ApiResult.success("공지사항이 삭제되었습니다.", null);
    }
}
