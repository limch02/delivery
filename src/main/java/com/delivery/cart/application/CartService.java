package com.delivery.cart.application;

import com.delivery.cart.application.dto.CartAddCommand;
import com.delivery.cart.application.dto.CartItemResult;
import com.delivery.cart.application.dto.CartResult;
import com.delivery.cart.domain.Cart;
import com.delivery.cart.domain.CartItem;
import com.delivery.cart.exception.CartErrorCode;
import com.delivery.cart.exception.CartException;
import com.delivery.cart.repository.CartRepository;
import com.delivery.member.application.MemberService;
import com.delivery.member.domain.Member;
import com.delivery.menu.application.MenuService;
import com.delivery.menu.domain.Menu;
import com.delivery.menu.exception.MenuErrorCode;
import com.delivery.menu.exception.MenuException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

	private final CartRepository cartRepository;
	private final MemberService memberService;
	private final MenuService menuService;

	public CartResult getCart(String email) {
		return CartResult.from(findCart(email));
	}

	public Cart findCart(String email) {
		return cartRepository.findByMemberEmailWithItems(email)
			.orElseThrow(() -> new CartException(CartErrorCode.CART_NOT_FOUND));
	}

	@Transactional
	public void clearCart(Cart cart) {
		cartRepository.delete(cart);
	}

	@Transactional
	public CartResult addToCart(String email, CartAddCommand command) {
		Menu menu = menuService.findMenuWithStore(command.menuId());

		if (menu.isSoldOut()) {
			throw new MenuException(MenuErrorCode.MENU_SOLD_OUT);
		}

		Cart cart = cartRepository.findByMemberEmailWithItems(email)
			.orElseGet(() -> {
				Member member = memberService.findMember(email);
				return cartRepository.save(new Cart(member, menu.getStore()));
			});

		if (cart.hasDifferentStore(menu.getStoreId())) {
			throw new CartException(CartErrorCode.DIFFERENT_STORE);
		}

		cart.addMenu(menu, command.quantity());

		return CartResult.from(cart);
	}

	@Transactional
	public CartItemResult increaseCartItemQuantity(String email, Long cartId, Long menuId) {
		Cart cart = findCartWithOwnerCheck(email, cartId);
		CartItem cartItem = cart.increaseQuantity(menuId)
			.orElseThrow(() -> new CartException(CartErrorCode.CART_ITEM_NOT_FOUND));
		return CartItemResult.from(cartItem);
	}

	@Transactional
	public void decreaseCartItemQuantity(String email, Long cartId, Long menuId) {
		Cart cart = findCartWithOwnerCheck(email, cartId);
		cart.decreaseQuantity(menuId)
			.orElseThrow(() -> new CartException(CartErrorCode.CART_ITEM_NOT_FOUND));
		if (cart.isCartItemEmpty()) {
			cartRepository.delete(cart);
		}
	}

	@Transactional
	public void deleteCartItem(String email, Long cartId, Long menuId) {
		Cart cart = findCartWithOwnerCheck(email, cartId);
		cart.removeItem(menuId)
			.orElseThrow(() -> new CartException(CartErrorCode.CART_ITEM_NOT_FOUND));
		if (cart.isCartItemEmpty()) {
			cartRepository.delete(cart);
		}
	}

	private Cart findCartWithOwnerCheck(String email, Long cartId) {
		Cart cart = cartRepository.findByIdWithItemsAndMember(cartId)
			.orElseThrow(() -> new CartException(CartErrorCode.CART_NOT_FOUND));
		if (!cart.isCreatedBy(email)) {
			throw new CartException(CartErrorCode.CART_ITEM_FORBIDDEN);
		}
		return cart;
	}
}
