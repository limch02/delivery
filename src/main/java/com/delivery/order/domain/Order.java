package com.delivery.order.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.delivery.cart.domain.Cart;
import com.delivery.member.domain.Member;
import com.delivery.store.domain.Store;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id", nullable = false, updatable = false)
	private Long order_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private OrderStatus status;

	@Column(name = "total_price", nullable = false)
	private int totalPrice;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public static Order from(Cart cart) {
		Order order = new Order();
		order.member = cart.getMember();
		order.store = cart.getStore();
		order.status = OrderStatus.PENDING;
		order.createdAt = LocalDateTime.now();

		cart.getCartItems().forEach(cartItem -> {
			OrderItem orderItem = new OrderItem(order, cartItem);
			order.orderItems.add(orderItem);
		});

		order.totalPrice = order.orderItems.stream().mapToInt(OrderItem::getSubtotal).sum();
		return order;
	}
}
