package com.delivery.member.presentation.dto;

import com.delivery.member.application.dto.SignUpResult;
import com.delivery.member.domain.Role;

public record SignUpResponse(
	Long member_id,
	String email,
	String phone_num,
	Role role,
	String address
) {
	public static SignUpResponse from(SignUpResult result) {
		return new SignUpResponse(
			result.member_id(),
			result.email(),
			result.phone_num(),
			result.role(),
			result.address()
		);
	}
}
