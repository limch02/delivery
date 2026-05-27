package com.delivery.cart.repository;

import java.util.Optional;

import com.delivery.cart.domain.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	@Query("SELECT ci FROM CartItem ci JOIN FETCH ci.cart c JOIN FETCH c.member WHERE ci.cart_item_id = :itemId")
	Optional<CartItem> findByIdWithCartAndMember(@Param("itemId") Long itemId);
}
