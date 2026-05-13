package com.delivery.cart.exception;

import com.delivery.common.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CartErrorCode implements ErrorCode {
	DIFFERENT_STORE("같은 가게의 메뉴만 담을 수 있습니다."),
	CART_ITEM_NOT_FOUND("장바구니 항목을 찾을 수 없습니다."),
	CART_ITEM_FORBIDDEN("본인의 장바구니 항목만 수정할 수 있습니다.");

	private final String message;
}
