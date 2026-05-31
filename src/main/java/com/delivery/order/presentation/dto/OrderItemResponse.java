package com.delivery.order.presentation.dto;

import com.delivery.order.application.dto.OrderItemResult;

public record OrderItemResponse(Long orderItemId, String menuName, int menuPrice, int quantity, int subtotal) {

	public static OrderItemResponse from(OrderItemResult result) {
		return new OrderItemResponse(
			result.orderItemId(),
			result.menuName(),
			result.menuPrice(),
			result.quantity(),
			result.subtotal()
		);
	}
}
