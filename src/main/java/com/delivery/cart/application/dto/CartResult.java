package com.delivery.cart.application.dto;

import java.util.List;

import com.delivery.cart.domain.Cart;
import com.delivery.cart.domain.CartItem;

public record CartResult(Long cartId, List<CartItemResult> items) {

	public static CartResult from(Cart cart, List<CartItem> cartItems) {
		List<CartItemResult> itemResults = cartItems.stream()
			.map(CartItemResult::from)
			.toList();
		return new CartResult(cart.getCart_id(), itemResults);
	}
}
