package com.delivery.member.presentation.dto;

import com.delivery.member.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(
	@Email
	@NotBlank(message = "이메일을 입력해주세요.")
	String email,
	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Max(50)
	String password,
	@NotBlank(message = "전화번호를 입력해주세요.")
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
	message = "전화번호 형식이 옳바르지 않습니다.")
	String phone_num,
	Role role,
	String address
) {
}
