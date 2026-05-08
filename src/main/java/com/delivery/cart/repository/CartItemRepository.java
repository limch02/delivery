package com.delivery.cart.repository;

import java.util.List;
import java.util.Optional;

import com.delivery.cart.domain.Cart;
import com.delivery.cart.domain.CartItem;
import com.delivery.menu.domain.Menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	@Query("SELECT ci FROM CartItem ci JOIN FETCH ci.menu m JOIN FETCH m.store WHERE ci.cart = :cart")
	List<CartItem> findByCartWithMenuAndStore(@Param("cart") Cart cart);

	Optional<CartItem> findByCartAndMenu(Cart cart, Menu menu);
}
