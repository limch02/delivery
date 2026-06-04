package com.delivery.order.presentation.dto;

import java.util.List;

import com.delivery.order.application.dto.OrderResult;
import com.delivery.order.domain.OrderStatus;

public record OrderResponse(Long orderId, OrderStatus status, List<OrderItemResponse> items, int totalPrice) {

	public static OrderResponse from(OrderResult result) {
		return new OrderResponse(
			result.orderId(),
			result.status(),
			result.items().stream().map(OrderItemResponse::from).toList(),
			result.totalPrice()
		);
	}
}
