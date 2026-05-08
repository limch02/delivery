package com.delivery.cart.repository;

import java.util.Optional;

import com.delivery.cart.domain.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query("""
		SELECT DISTINCT c
		FROM Cart c
		LEFT JOIN FETCH c.store
		LEFT JOIN FETCH c.cartItems ci
		LEFT JOIN FETCH ci.menu
		WHERE c.member.email = :email
		""")
	Optional<Cart> findByMemberEmailWithItems(@Param("email") String email);
}
