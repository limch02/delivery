package com.delivery.order.presentation;

import com.delivery.common.response.ApiResponse;
import com.delivery.order.application.OrderService;
import com.delivery.order.presentation.dto.OrderResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
		@AuthenticationPrincipal UserDetails userDetails
	) {
		OrderResponse response = OrderResponse.from(
			orderService.createOrder(userDetails.getUsername())
		);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
	}
}
