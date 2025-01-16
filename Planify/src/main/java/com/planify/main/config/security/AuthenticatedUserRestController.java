package com.planify.main.config.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planify.main.api.member.domain.Member;
import com.planify.main.common.ApiResult;

@RestController
public class AuthenticatedUserRestController {

	// member의 정보를 반환
	@GetMapping("/api/user/info") 
	public ApiResult<Member> getAuthenticatedUserInfo (){
		
		Member authenticatedUser = AuthenticatedUserUtil.getAuthenticatedUser();
		
		 // 인증된 유저가 없을 경우 401 Unauthorized 반환
        if (authenticatedUser == null) {
            return ApiResult.failure( HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
		
		return ApiResult.success(authenticatedUser);
	}
}
