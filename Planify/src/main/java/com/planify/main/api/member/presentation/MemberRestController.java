package com.planify.main.api.member.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planify.main.api.member.application.MemberService;
import com.planify.main.api.member.application.dto.MemberDTO;
import com.planify.main.common.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/profile")
@Tag(name = "사용자 프로필 API", description = "사용자의 프로필을 관리하는 API")
public class MemberRestController {

    private final MemberService memberService;
    
    // 사용자 정보 조회
    @GetMapping
    @Operation(summary = "사용자 프로필 조회", description = "현재 로그인된 사용자의 프로필을 조회합니다.")
    public ApiResult<MemberDTO> getProfile() {
        MemberDTO memberDTO = memberService.getMemberInfo();
        return ApiResult.success(memberDTO);
    }

    // 사용자 수정
    @PutMapping("/update")
    @Operation(summary = "사용자 프로필 수정", description = "현재 로그인된 사용자의 프로필을 수정합니다.")
    public ApiResult<Void> updateProfile(@RequestBody MemberDTO memberDTO) {
        memberService.updateMemberInfo(memberDTO);
        return ApiResult.success("정보가 수정되었습니다.", null);
    }
}
