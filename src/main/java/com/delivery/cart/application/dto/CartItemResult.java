package com.delivery.cart.application.dto;

import com.delivery.cart.domain.CartItem;

public record CartItemResult(Long menuId, String menuName, int price, int quantity) {

	public static CartItemResult from(CartItem cartItem) {
		return new CartItemResult(
			cartItem.getMenu().getMenu_id(),
			cartItem.getMenu().getName(),
			cartItem.getMenu().getPrice(),
			cartItem.getQuantity()
		);
	}
}
