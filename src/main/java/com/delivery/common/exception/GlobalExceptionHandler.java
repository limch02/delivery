package com.delivery.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.delivery.common.response.ApiResponse;
import com.delivery.member.exception.MemberException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MemberException.class)
	public ResponseEntity<ApiResponse<?>> handleMemberException(MemberException e) {
		HttpStatus status = switch (e.getErrorCode()) {
			case DUPLICATE_EMAIL -> HttpStatus.CONFLICT;
			case MEMBER_NOT_FOUND -> HttpStatus.NOT_FOUND;
			case INVALID_PASSWORD -> HttpStatus.UNAUTHORIZED;
			case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
		};
		return ResponseEntity
			.status(status)
			.body(ApiResponse.fail(e.getMessage()));
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e) {
		CommonErrorCode errorCode = e.getErrorCode();
		return ResponseEntity
			.status(errorCode.getStatus())
			.body(ApiResponse.fail(errorCode.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException e) {
		FieldError fieldError = e.getBindingResult().getFieldError();
		String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : CommonErrorCode.INVALID_INPUT.getMessage();
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
