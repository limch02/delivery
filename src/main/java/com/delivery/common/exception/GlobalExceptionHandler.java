package com.delivery.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.delivery.common.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e) {
		return ResponseEntity
			.status(e.getStatus())
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
