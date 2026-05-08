package com.delivery.cart.exception;

import com.delivery.common.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CartErrorCode implements ErrorCode {
	DIFFERENT_STORE("같은 가게의 메뉴만 담을 수 있습니다.");

	private final String message;
}
