package com.delivery.member.exception;

import com.delivery.common.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
	DUPLICATE_EMAIL("이미 사용 중인 이메일입니다."),
	MEMBER_NOT_FOUND("존재하지 않는 회원입니다."),
	INVALID_PASSWORD("비밀번호가 일치하지 않습니다."),
	UNAUTHORIZED("로그인이 필요합니다.");

	private final String message;
}
