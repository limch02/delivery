package com.delivery.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.common.response.ApiResponse;
import com.delivery.member.application.MemberService;
import com.delivery.member.application.dto.MemberResult;
import com.delivery.member.presentation.dto.MeResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@GetMapping("/me")
	public ResponseEntity<ApiResponse<MeResponse>> getMe(@AuthenticationPrincipal UserDetails userDetails) {
		MemberResult result = memberService.getMemberByEmail(userDetails.getUsername());
		return ResponseEntity.ok(ApiResponse.success(MeResponse.from(result)));
	}
}
