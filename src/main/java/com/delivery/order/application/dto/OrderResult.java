package com.delivery.order.application.dto;

import java.util.List;

import com.delivery.order.domain.Order;
import com.delivery.order.domain.OrderStatus;

public record OrderResult(Long orderId, OrderStatus status, List<OrderItemResult> items, int totalPrice) {

	public static OrderResult from(Order order) {
		List<OrderItemResult> itemResults = order.getOrderItems().stream()
			.map(OrderItemResult::from)
			.toList();
		return new OrderResult(order.getOrder_id(), order.getStatus(), itemResults, order.getTotalPrice());
	}
}
