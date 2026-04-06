package com.delivery.member.exception;

import com.delivery.common.exception.CustomException;

public class MemberException extends CustomException {
	private final MemberErrorCode errorCode;

	public MemberException(MemberErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	public MemberErrorCode getMemberErrorCode() {
		return errorCode;
	}
}
