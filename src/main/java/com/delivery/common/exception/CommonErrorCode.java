package com.delivery.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode {
	// 공통
	INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),

	// 회원
	DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),

	// 가게
	STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 가게입니다."),

	// 장바구니
	CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니 항목을 찾을 수 없습니다."),
	DIFFERENT_STORE(HttpStatus.BAD_REQUEST, "같은 가게의 메뉴만 담을 수 있습니다."),
	CART_OWNER_MISMATCH(HttpStatus.FORBIDDEN, "본인의 장바구니만 수정할 수 있습니다."),

	// 주문
	ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 주문입니다."),
	ORDER_OWNER_MISMATCH(HttpStatus.FORBIDDEN, "본인의 주문만 조회할 수 있습니다."),
	INVALID_ORDER_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 주문 상태 전이입니다."),
	EMPTY_CART(HttpStatus.BAD_REQUEST, "장바구니가 비어있습니다.");

	private final HttpStatus status;
	private final String message;
}
