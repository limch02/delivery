package com.delivery.member.presentation.dto;

import com.delivery.member.application.dto.SignUpCommand;
import com.delivery.member.domain.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(
	@Email
	@NotBlank(message = "이메일을 입력해주세요.")
	String email,
	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Size(max = 50, message = "비밀번호는 최대 50자입니다.")
	String password,
	@NotBlank(message = "전화번호를 입력해주세요.")
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 옳바르지 않습니다.")
	String phone_num,
	Role role,
	String address
) {
	public SignUpCommand toCommand() {
		return new SignUpCommand(email, password, phone_num, role, address);
	}
}
