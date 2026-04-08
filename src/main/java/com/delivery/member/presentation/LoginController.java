package com.delivery.member.presentation;

import java.time.Duration;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.common.response.ApiResponse;
import com.delivery.member.application.dto.LoginCommand;
import com.delivery.member.presentation.dto.LoginRequest;
import com.delivery.member.application.MemberService;

import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @Value("${cookie.secure}")
    private boolean cookieSecure;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@Valid @RequestBody LoginRequest request,
		HttpServletResponse response) {
        LoginCommand command = request.toCommand();
        String token = memberService.login(command);
		ResponseCookie cookie = ResponseCookie.from("accessToken",token)
			.httpOnly(true)
			.secure(cookieSecure)
			.path("/")
			.maxAge(Duration.ofHours(1))
			.sameSite("Strict")
			.build();
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
