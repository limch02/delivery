package com.delivery.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.delivery.cart.exception.CartException;
import com.delivery.common.response.ApiResponse;
import com.delivery.member.exception.MemberException;
import com.delivery.menu.exception.MenuException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MemberException.class)
	public ResponseEntity<ApiResponse<?>> handleMemberException(MemberException e) {
		HttpStatus status = switch (e.getMemberErrorCode()) {
			case DUPLICATE_EMAIL -> HttpStatus.CONFLICT;
			case MEMBER_NOT_FOUND -> HttpStatus.NOT_FOUND;
			case INVALID_PASSWORD, UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
		};
		return ResponseEntity
			.status(status)
			.body(ApiResponse.fail(e.getErrorMessage()));
	}

	@ExceptionHandler(MenuException.class)
	public ResponseEntity<ApiResponse<?>> handleMenuException(MenuException e) {
		HttpStatus status = switch (e.getMenuErrorCode()) {
			case MENU_NOT_FOUND -> HttpStatus.NOT_FOUND;
			case NOT_MENU_OWNER -> HttpStatus.FORBIDDEN;
			case MENU_SOLD_OUT -> HttpStatus.BAD_REQUEST;
		};
		return ResponseEntity
			.status(status)
			.body(ApiResponse.fail(e.getErrorMessage()));
	}

	@ExceptionHandler(CartException.class)
	public ResponseEntity<ApiResponse<?>> handleCartException(CartException e) {
		HttpStatus status = switch (e.getCartErrorCode()) {
			case DIFFERENT_STORE -> HttpStatus.BAD_REQUEST;
			case CART_ITEM_NOT_FOUND -> HttpStatus.NOT_FOUND;
			case CART_ITEM_FORBIDDEN -> HttpStatus.FORBIDDEN;
		};
		return ResponseEntity
			.status(status)
			.body(ApiResponse.fail(e.getErrorMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException e) {
		FieldError fieldError = e.getBindingResult().getFieldError();
		String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "잘못된 입력값입니다.";
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.fail(errorMessage));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiResponse.fail("서버 오류가 발생했습니다."));
	}
}
