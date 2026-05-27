package com.delivery.cart.presentation.dto;

import com.delivery.cart.application.dto.CartAddCommand;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartAddRequest(
	@NotNull(message = "메뉴 ID는 필수입니다.")
	Long menuId,

	@Min(value = 1, message = "수량은 1 이상이어야 합니다.")
	int quantity
) {
	public CartAddCommand toCommand() {
		return new CartAddCommand(menuId, quantity);
	}
}
