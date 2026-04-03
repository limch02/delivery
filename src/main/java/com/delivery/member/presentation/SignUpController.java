package com.delivery.member.presentation;

import com.delivery.common.response.ApiResponse;
import com.delivery.member.application.MemberService;
import com.delivery.member.application.dto.SignUpCommand;
import com.delivery.member.application.dto.SignUpResult;
import com.delivery.member.presentation.dto.SignUpRequest;
import com.delivery.member.presentation.dto.SignUpResponse;

import jakarta.validation.Valid;
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
		@Valid @RequestBody SignUpRequest request
	) {
		SignUpCommand command = request.toCommand();
		SignUpResult result = memberService.signUp(command);
		return ResponseEntity.ok(ApiResponse.success(SignUpResponse.from(result)));
	}
}
