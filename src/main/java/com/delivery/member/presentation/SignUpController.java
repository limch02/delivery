package com.delivery.member.presentation;

import com.delivery.member.application.MemberService;
import com.delivery.member.application.dto.SignUpCommand;
import com.delivery.member.presentation.dto.SignUpRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<Void> signUp(
		@Valid @RequestBody SignUpRequest request
	) {
		SignUpCommand command = request.toCommand();
		memberService.signUp(command);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
