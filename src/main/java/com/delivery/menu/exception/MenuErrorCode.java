package com.delivery.menu.exception;

import com.delivery.common.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode {
	MENU_NOT_FOUND("존재하지 않는 메뉴입니다."),
	NOT_MENU_OWNER("해당 가게의 사장님만 메뉴를 등록할 수 있습니다.");

	private final String message;
}
