package com.delivery.member.application.dto;

import com.delivery.member.domain.Role;

public record SignUpCommand(
	String email,
	String password,
	String phone_num,
	Role role,
	String address
) {
}
