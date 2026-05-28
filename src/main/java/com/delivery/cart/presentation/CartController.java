package com.delivery.cart.presentation;

import com.delivery.cart.application.CartService;
import com.delivery.cart.presentation.dto.CartAddRequest;
import com.delivery.cart.presentation.dto.CartItemResponse;
import com.delivery.cart.presentation.dto.CartResponse;
import com.delivery.common.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	@PostMapping
	public ResponseEntity<ApiResponse<CartResponse>> addToCart(
		@AuthenticationPrincipal UserDetails userDetails,
		@Valid @RequestBody CartAddRequest request
	) {
		CartResponse response = CartResponse.from(
			cartService.addToCart(userDetails.getUsername(), request.toCommand())
		);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
	}

	@PatchMapping("/{cartId}/items/{menuId}/increase")
	public ResponseEntity<ApiResponse<CartItemResponse>> increaseCartItem(
		@AuthenticationPrincipal UserDetails userDetails,
		@PathVariable Long cartId,
		@PathVariable Long menuId
	) {
		CartItemResponse response = CartItemResponse.from(
			cartService.increaseCartItemQuantity(userDetails.getUsername(), cartId, menuId)
		);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@PatchMapping("/{cartId}/items/{menuId}/decrease")
	public ResponseEntity<ApiResponse<Void>> decreaseCartItem(
		@AuthenticationPrincipal UserDetails userDetails,
		@PathVariable Long cartId,
		@PathVariable Long menuId
	) {
		cartService.decreaseCartItemQuantity(userDetails.getUsername(), cartId, menuId);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	@DeleteMapping("/{cartId}/items/{menuId}")
	public ResponseEntity<Void> deleteCartItem(
		@AuthenticationPrincipal UserDetails userDetails,
		@PathVariable Long cartId,
		@PathVariable Long menuId
	) {
		cartService.deleteCartItem(userDetails.getUsername(), cartId, menuId);
		return ResponseEntity.noContent().build();
	}
}
