package com.planify.main.api.member.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planify.main.api.member.application.MemberService;
import com.planify.main.api.member.application.dto.MemberDTO;
import com.planify.main.common.ApiResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/profile")
public class MemberRestController {

    private final MemberService memberService;
    
    // 사용자 정보 조회
    @GetMapping
    public ApiResult<MemberDTO> getProfile() {
        MemberDTO memberDTO = memberService.getMemberInfo();
        return ApiResult.success(memberDTO);
    }

    // 사용자 수정
    @PutMapping("/update")
    public ApiResult<Void> updateProfile(@RequestBody MemberDTO memberDTO) {
        memberService.updateMemberInfo(memberDTO);
        return ApiResult.success("정보가 수정되었습니다.", null);
    }
}
