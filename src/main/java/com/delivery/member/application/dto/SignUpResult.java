package com.delivery.member.application.dto;

import com.delivery.member.domain.Role;

public record SignUpResult(
	Long member_id,
	String email,
	String phone_num,
	Role role,
	String address
) {
}
