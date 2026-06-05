package com.delivery.order.application;

import com.delivery.cart.application.CartService;
import com.delivery.cart.domain.Cart;
import com.delivery.order.application.dto.OrderResult;
import com.delivery.order.domain.Order;
import com.delivery.order.exception.OrderErrorCode;
import com.delivery.order.exception.OrderException;
import com.delivery.order.repository.OrderRepository;
import com.delivery.store.application.StoreService;
import com.delivery.store.domain.Store;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final CartService cartService;
	private final StoreService storeService;

	@Transactional
	public OrderResult createOrder(String email) {
		Cart cart = cartService.findNonEmptyCart(email);

		Order order = Order.from(cart);
		orderRepository.save(order);

		cartService.clearCart(cart);

		return OrderResult.from(order);
	}

	@Transactional
	public OrderResult advanceOrderStatus(String email, Long orderId) {
		Order order = findOrder(orderId);

		Store store = storeService.findStore(order.storeId());
		if (!store.isOwnedBy(email)) {
			throw new OrderException(OrderErrorCode.NOT_ORDER_STORE_OWNER);
		}

		order.advanceStatus();
		return OrderResult.from(order);
	}

	private Order findOrder(Long orderId) {
		return orderRepository.findById(orderId)
			.orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));
	}
}
