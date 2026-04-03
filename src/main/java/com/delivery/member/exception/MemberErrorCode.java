package com.delivery.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;

import com.delivery.common.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
	DUPLICATE_EMAIL(HttpStatus.CONFLICT,"이미 사용 중인 이메일입니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 회원입니다."),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"로그인이 필요합니다.");
	private final HttpStatus status;
	private final String message;


	@Override
	public HttpStatus getStatus() {
		return status;
	}
}
