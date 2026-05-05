package com.delivery.menu.exception;

import com.delivery.common.exception.CustomException;

public class MenuException extends CustomException {
	private final MenuErrorCode errorCode;

	public MenuException(MenuErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	public MenuErrorCode getMenuErrorCode() {
		return errorCode;
	}
}
