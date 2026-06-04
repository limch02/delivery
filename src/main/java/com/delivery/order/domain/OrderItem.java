package com.delivery.order.domain;

import com.delivery.cart.domain.CartItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id", nullable = false, updatable = false)
	private Long order_item_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@Column(name = "menu_name", nullable = false)
	private String menuName;

	@Column(name = "menu_price", nullable = false)
	private int menuPrice;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	public OrderItem(Order order, CartItem cartItem) {
		this.order = order;
		this.menuName = cartItem.getMenu().getName();
		this.menuPrice = cartItem.getMenu().getPrice();
		this.quantity = cartItem.getQuantity();
	}

	public int getSubtotal() {
		return menuPrice * quantity;
	}
}
