package com.delivery.member.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;
import com.delivery.common.exception.CustomException;

@Getter
public class MemberException extends CustomException {
	private final MemberErrorCode memberErrorCode;

	public MemberException(MemberErrorCode errorCode) {
		super(null); // Delegate to CustomException with null CommonErrorCode
		this.memberErrorCode = errorCode;
	}

	@Override
	public HttpStatus getStatus() {
		return switch (memberErrorCode) {
			case DUPLICATE_EMAIL -> HttpStatus.CONFLICT;
			case MEMBER_NOT_FOUND -> HttpStatus.NOT_FOUND;
			case INVALID_PASSWORD, UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
		};
	}

	@Override
	public String getErrorMessage() {
		return memberErrorCode.getMessage();
	}
}
