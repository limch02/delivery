package com.delivery.order.application;

import com.delivery.cart.application.CartService;
import com.delivery.cart.domain.Cart;
import com.delivery.order.application.dto.OrderResult;
import com.delivery.order.domain.Order;
import com.delivery.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final CartService cartService;

	@Transactional
	public OrderResult createOrder(String email) {
		Cart cart = cartService.findNonEmptyCart(email);

		Order order = Order.from(cart);
		orderRepository.save(order);

		cartService.clearCart(cart);

		return OrderResult.from(order);
	}
}
