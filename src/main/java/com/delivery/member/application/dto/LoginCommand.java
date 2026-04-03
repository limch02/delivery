package com.delivery.member.application.dto;

public record LoginCommand(
	String email,
	String password
) {
}
