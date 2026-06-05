package com.delivery.order.exception;

import com.delivery.common.exception.CustomException;

public class OrderException extends CustomException {
	private final OrderErrorCode errorCode;

	public OrderException(OrderErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	public OrderErrorCode getOrderErrorCode() {
		return errorCode;
	}
}
