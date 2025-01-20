package com.planify.main.api.member.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planify.main.api.member.application.AdminMemberService;
import com.planify.main.api.member.application.dto.MemberDTO;
import com.planify.main.common.ApiResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/members")
public class AdminMemberRestController {
	
    private final AdminMemberService adminMemberService;
    
    /**
     * 사용자 목록 조회
     */
    @GetMapping
    public ApiResult<List<MemberDTO>> getAllMembers() {
        List<MemberDTO> members = adminMemberService.getAllMembers();
        return ApiResult.success(members);
    }
    
    /**
     * 사용자 상세 조회
     */
    @GetMapping("/{memberNo}")
    public ApiResult<MemberDTO> getMember(@PathVariable Long memberNo) {
        MemberDTO memberDTO = adminMemberService.getMemberById(memberNo);
        return ApiResult.success(memberDTO);
    }
    
    /**
     * 신규 사용자 등록
     */
    @PostMapping
    public ApiResult<Void> createMember(@RequestBody MemberDTO memberDTO) {
        adminMemberService.createMember(memberDTO);
        return ApiResult.success("사용자가 등록되었습니다.", null);
    }
    
    /**
     * 사용자 정보 수정
     */
    @PutMapping("/{memberNo}")
    public ApiResult<Void> updateMember(@PathVariable Long memberNo, @RequestBody MemberDTO memberDTO) {
        adminMemberService.updateMember(memberNo, memberDTO);
        return ApiResult.success("사용자 정보가 수정되었습니다.", null);
    }

    /**
     * 사용자 삭제
     */
    @DeleteMapping("/{memberNo}")
    public ApiResult<Void> deleteMember(@PathVariable Long memberNo) {
        adminMemberService.deleteMember(memberNo);
        return ApiResult.success("사용자가 삭제되었습니다.", null);
    }
}
