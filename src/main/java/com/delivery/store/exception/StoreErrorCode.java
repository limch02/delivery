package com.delivery.store.exception;

import com.delivery.common.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements ErrorCode {
	STORE_NOT_FOUND("존재하지 않는 가게입니다."),
	NOT_STORE_OWNER("가게 사장님만 접근할 수 있습니다.");

	private final String message;
}
