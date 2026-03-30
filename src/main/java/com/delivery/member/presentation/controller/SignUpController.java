package com.delivery.member.presentation.controller;

import com.delivery.common.response.ApiResponse;
import com.delivery.member.application.service.MemberService;
import com.delivery.member.presentation.dto.SignUpRequest;
import com.delivery.member.presentation.dto.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
	private final MemberService memberService;

	@PostMapping
	public ResponseEntity<ApiResponse<SignUpResponse>> signUp(
		@RequestBody SignUpRequest request
	) {
		SignUpResponse response = memberService.signUp(request);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
