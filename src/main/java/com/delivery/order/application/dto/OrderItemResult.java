package com.delivery.order.application.dto;

import com.delivery.order.domain.OrderItem;

public record OrderItemResult(Long orderItemId, String menuName, int menuPrice, int quantity, int subtotal) {

	public static OrderItemResult from(OrderItem orderItem) {
		return new OrderItemResult(
			orderItem.getOrder_item_id(),
			orderItem.getMenuName(),
			orderItem.getMenuPrice(),
			orderItem.getQuantity(),
			orderItem.getSubtotal()
		);
	}
}
