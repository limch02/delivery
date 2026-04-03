package com.delivery.member.presentation;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.common.response.ApiResponse;
import com.delivery.member.application.dto.LoginCommand;
import com.delivery.member.application.dto.LoginResult;
import com.delivery.member.presentation.dto.LoginRequest;
import com.delivery.member.presentation.dto.LoginResponse;
import com.delivery.member.application.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginCommand command = request.toCommand();
        LoginResult result = memberService.login(command);
        return ResponseEntity.ok(ApiResponse.success(LoginResponse.from(result)));
    }
}
