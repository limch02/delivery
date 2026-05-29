package com.delivery.cart.application.dto;

import java.util.List;

import com.delivery.cart.domain.Cart;

public record CartResult(Long cartId, List<CartItemResult> items, int totalPrice) {

	public static CartResult from(Cart cart) {
		List<CartItemResult> itemResults = cart.getCartItems().stream()
			.map(CartItemResult::from)
			.toList();
		return new CartResult(cart.getCart_id(), itemResults, cart.getTotalPrice());
	}
}
