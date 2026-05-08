package com.delivery.cart.presentation.dto;

import java.util.List;

import com.delivery.cart.application.dto.CartResult;

public record CartResponse(Long cartId, List<CartItemResponse> items) {

	public static CartResponse from(CartResult result) {
		return new CartResponse(
			result.cartId(),
			result.items().stream().map(CartItemResponse::from).toList()
		);
	}
}
