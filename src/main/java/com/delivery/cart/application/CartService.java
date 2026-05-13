package com.delivery.cart.application;

import com.delivery.cart.application.dto.CartAddCommand;
import com.delivery.cart.application.dto.CartItemResult;
import com.delivery.cart.application.dto.CartResult;
import com.delivery.cart.domain.Cart;
import com.delivery.cart.domain.CartItem;
import com.delivery.cart.exception.CartErrorCode;
import com.delivery.cart.exception.CartException;
import com.delivery.cart.repository.CartItemRepository;
import com.delivery.cart.repository.CartRepository;
import com.delivery.member.domain.Member;
import com.delivery.member.repository.MemberRepository;
import com.delivery.menu.domain.Menu;
import com.delivery.menu.exception.MenuErrorCode;
import com.delivery.menu.exception.MenuException;
import com.delivery.menu.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final MemberRepository memberRepository;
	private final MenuRepository menuRepository;

	@Transactional
	public CartResult addToCart(String email, CartAddCommand command) {
		Member member = memberRepository.findByEmail(email).orElseThrow();

		Menu menu = menuRepository.findByIdWithStore(command.menuId())
			.orElseThrow(() -> new MenuException(MenuErrorCode.MENU_NOT_FOUND));

		if (menu.isSoldOut()) {
			throw new MenuException(MenuErrorCode.MENU_SOLD_OUT);
		}

		Cart cart = cartRepository.findByMemberEmailWithItems(email)
			.orElseGet(() -> cartRepository.save(new Cart(member, menu.getStore())));

		if (cart.hasDifferentStore(menu.getStoreId())) {
			throw new CartException(CartErrorCode.DIFFERENT_STORE);
		}

		cart.findItemByMenuId(menu.getMenu_id())
			.ifPresentOrElse(
				existing -> existing.addQuantity(command.quantity()),
				() -> cartItemRepository.save(cart.addItem(menu, command.quantity()))
			);

		return CartResult.from(cart, cart.getCartItems());
	}

	@Transactional
	public CartItemResult increaseCartItemQuantity(String email, Long itemId) {
		CartItem cartItem = findCartItemWithOwnerCheck(email, itemId);
		cartItem.increaseQuantity();
		return CartItemResult.from(cartItem);
	}

	@Transactional
	public void decreaseCartItemQuantity(String email, Long itemId) {
		CartItem cartItem = findCartItemWithOwnerCheck(email, itemId);
		if (cartItem.getQuantity() == 1) {
			cartItemRepository.delete(cartItem);
			return;
		}
		cartItem.decreaseQuantity();
	}

	private CartItem findCartItemWithOwnerCheck(String email, Long itemId) {
		CartItem cartItem = cartItemRepository.findByIdWithCartAndMember(itemId)
			.orElseThrow(() -> new CartException(CartErrorCode.CART_ITEM_NOT_FOUND));
		if (!cartItem.getCart().isCreatedBy(email)) {
			throw new CartException(CartErrorCode.CART_ITEM_FORBIDDEN);
		}
		return cartItem;
	}
}
