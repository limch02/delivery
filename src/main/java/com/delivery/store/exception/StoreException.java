package com.delivery.store.exception;

import com.delivery.common.exception.CustomException;

public class StoreException extends CustomException {
	private final StoreErrorCode errorCode;

	public StoreException(StoreErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	public StoreErrorCode getStoreErrorCode() {
		return errorCode;
	}
}
