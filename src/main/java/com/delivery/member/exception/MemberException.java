package com.delivery.member.exception;

import com.delivery.common.exception.CustomException;

public class MemberException extends CustomException {
	public MemberException(MemberErrorCode errorCode) {
		super(errorCode);
	}
}
