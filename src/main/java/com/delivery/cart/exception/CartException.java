package com.delivery.cart.exception;

import com.delivery.common.exception.CustomException;

public class CartException extends CustomException {
	private final CartErrorCode errorCode;

	public CartException(CartErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	public CartErrorCode getCartErrorCode() {
		return errorCode;
	}
}
