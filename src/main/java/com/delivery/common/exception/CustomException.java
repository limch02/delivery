package com.delivery.common.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public abstract class CustomException extends RuntimeException {
	private final ErrorCode errorCode;

	public CustomException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public HttpStatus getStatus() {
		if (errorCode != null) {
			return errorCode.getStatus();
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public String getErrorMessage() {
		if (errorCode != null) {
			return errorCode.getMessage();
		}
		return getMessage();
	}
}
