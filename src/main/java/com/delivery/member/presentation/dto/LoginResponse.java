package com.delivery.member.presentation.dto;

import com.delivery.member.application.dto.LoginResult;

public record LoginResponse(
	String accessToken
) {
	public static LoginResponse from(LoginResult result) {
		return new LoginResponse(result.accessToken());
	}
}
