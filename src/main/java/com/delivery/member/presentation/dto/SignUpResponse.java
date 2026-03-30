package com.delivery.member.presentation.dto;

import com.delivery.member.entity.Role;

public record SignUpResponse(
	Long member_id,
	String email,
	String phone_num,
	Role role,
	String address
) {
}
