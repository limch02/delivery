package com.delivery.member.presentation.dto;

import com.delivery.member.entity.Role;

public record SignUpRequest(
	String email,
	String password,
	String phone_num,
	Role role,
	String address
) {
}
