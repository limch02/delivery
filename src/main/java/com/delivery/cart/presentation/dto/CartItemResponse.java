package com.delivery.cart.presentation.dto;

import com.delivery.cart.application.dto.CartItemResult;

public record CartItemResponse(Long menuId, String menuName, int price, int quantity) {

	public static CartItemResponse from(CartItemResult result) {
		return new CartItemResponse(result.menuId(), result.menuName(), result.price(), result.quantity());
	}
}
