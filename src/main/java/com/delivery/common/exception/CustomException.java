package com.delivery.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final CommonErrorCode errorCode;

	public CustomException(CommonErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
