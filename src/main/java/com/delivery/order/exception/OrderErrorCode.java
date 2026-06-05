package com.delivery.order.exception;

import com.delivery.common.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {
	ORDER_NOT_FOUND("존재하지 않는 주문입니다."),
	NOT_ORDER_STORE_OWNER("가게 사장님만 주문 상태를 변경할 수 있습니다."),
	INVALID_STATUS_TRANSITION("유효하지 않은 주문 상태 전이입니다.");

	private final String message;
}
