package com.delivery.member.application.dto;

public record SignUpCommand(
	String email,
	String password,
	String phone_num,
	String address
) {
}
