package com.delivery.cart.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.delivery.member.domain.Member;
import com.delivery.menu.domain.Menu;
import com.delivery.store.domain.Store;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "carts")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id", nullable = false, updatable = false)
	private Long cart_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, unique = true)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> cartItems = new ArrayList<>();

	public Cart(Member member, Store store) {
		this.member = member;
		this.store = store;
	}

	public boolean hasDifferentStore(Long storeId) {
		return !store.getStore_id().equals(storeId);
	}

	private Optional<CartItem> findItemByMenuId(Long menuId) {
		return cartItems.stream()
			.filter(cartItem -> cartItem.getMenu().getMenu_id().equals(menuId))
			.findFirst();
	}

	public boolean isCartItemEmpty(){
		return cartItems.isEmpty();
	}

	public boolean isCreatedBy(String email) {
		return member.matchesEmail(email);
	}

	public int getTotalPrice() {
		return cartItems.stream().mapToInt(CartItem::getSubtotal).sum();
	}

	public void addMenu(Menu menu, int quantity) {
		findItemByMenuId(menu.getMenu_id())
			.ifPresentOrElse(
				existing -> existing.addQuantity(quantity),
				() -> this.cartItems.add(new CartItem(this, menu, quantity))
			);
	}

	public Optional<CartItem> increaseQuantity(Long menuId) {
		return findItemByMenuId(menuId).map(cartItem -> {
			cartItem.increaseQuantity();
			return cartItem;
		});
	}

	public Optional<CartItem> decreaseQuantity(Long menuId) {
		return findItemByMenuId(menuId).map(cartItem -> {
			if (cartItem.getQuantity() == 1) {
				cartItems.remove(cartItem);
			} else {
				cartItem.decreaseQuantity();
			}
			return cartItem;
		});
	}

	public Optional<CartItem> removeItem(Long menuId) {
		return findItemByMenuId(menuId).map(cartItem -> {
			cartItems.remove(cartItem);
			return cartItem;
		});
	}
}
