package com.delivery.cart.application;

import com.delivery.cart.application.dto.CartAddCommand;
import com.delivery.cart.application.dto.CartItemResult;
import com.delivery.cart.application.dto.CartResult;
import com.delivery.cart.domain.Cart;
import com.delivery.cart.domain.CartItem;
import com.delivery.cart.exception.CartErrorCode;
import com.delivery.cart.exception.CartException;
import com.delivery.cart.repository.CartRepository;
import com.delivery.member.domain.Member;
import com.delivery.member.exception.MemberErrorCode;
import com.delivery.member.exception.MemberException;
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
	private final MemberRepository memberRepository;
	private final MenuRepository menuRepository;

	@Transactional
	public CartResult addToCart(String email, CartAddCommand command) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));


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

		cart.addMenu(menu, command.quantity());

		return CartResult.from(cart, cart.getCartItems());
	}

	@Transactional
	public CartItemResult increaseCartItemQuantity(String email, Long cartId, Long menuId) {
		Cart cart = findCartWithOwnerCheck(email, cartId);
		CartItem cartItem = cart.findItemByMenuId(menuId)
			.orElseThrow(() -> new CartException(CartErrorCode.CART_ITEM_NOT_FOUND));
		cartItem.increaseQuantity();
		return CartItemResult.from(cartItem);
	}

	@Transactional
	public void decreaseCartItemQuantity(String email, Long cartId, Long menuId) {
		Cart cart = findCartWithOwnerCheck(email, cartId);
		CartItem cartItem = cart.findItemByMenuId(menuId)
			.orElseThrow(() -> new CartException(CartErrorCode.CART_ITEM_NOT_FOUND));

		if (cartItem.getQuantity() == 1) {
			cart.getCartItems().remove(cartItem);
			if (cart.getCartItems().isEmpty()) {
				cartRepository.delete(cart);
			}
			return;
		}
		cartItem.decreaseQuantity();
	}

	@Transactional
	public void deleteCartItem(String email, Long cartId, Long menuId) {
		Cart cart = findCartWithOwnerCheck(email, cartId);
		CartItem cartItem = cart.findItemByMenuId(menuId)
			.orElseThrow(() -> new CartException(CartErrorCode.CART_ITEM_NOT_FOUND));

		cart.removeItem(cartItem);
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
