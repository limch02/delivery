package com.delivery.menu.application;

import java.util.List;

import com.delivery.menu.application.dto.MenuCreateCommand;
import com.delivery.menu.application.dto.MenuResult;
import com.delivery.menu.application.dto.MenuUpdateCommand;
import com.delivery.menu.domain.Menu;
import com.delivery.menu.exception.MenuErrorCode;
import com.delivery.menu.exception.MenuException;
import com.delivery.menu.repository.MenuRepository;
import com.delivery.store.domain.Store;
import com.delivery.store.exception.StoreErrorCode;
import com.delivery.store.exception.StoreException;
import com.delivery.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

	private final MenuRepository menuRepository;
	private final StoreRepository storeRepository;

	public List<MenuResult> getMenus(Long storeId) {
		return menuRepository.findAllByStoreId(storeId)
				.stream()
				.map(MenuResult::from)
				.toList();
	}

	@Transactional
	public void updateMenu(String email, Long storeId, Long menuId, MenuUpdateCommand command) {
		Menu menu = menuRepository.findByIdAndStoreIdWithOwner(menuId, storeId)
				.orElseThrow(() -> new MenuException(MenuErrorCode.MENU_NOT_FOUND));

		if(!menu.validateOwner(email)){
			throw new StoreException(StoreErrorCode.NOT_STORE_OWNER);
		}
		menu.update(command.name(), command.price(), command.description());
	}

	@Transactional
	public void deleteMenu(String email, Long storeId, Long menuId) {
		Menu menu = menuRepository.findByIdAndStoreIdWithOwner(menuId, storeId)
				.orElseThrow(() -> new MenuException(MenuErrorCode.MENU_NOT_FOUND));

		if(!menu.validateOwner(email)){
			throw new StoreException(StoreErrorCode.NOT_STORE_OWNER);
		}
		menuRepository.delete(menu);
	}

	@Transactional
	public void toggleSoldOut(String email, Long storeId, Long menuId) {
		Menu menu = menuRepository.findByIdAndStoreIdWithOwner(menuId, storeId)
				.orElseThrow(() -> new MenuException(MenuErrorCode.MENU_NOT_FOUND));

		if(!menu.validateOwner(email)){
			throw new StoreException(StoreErrorCode.NOT_STORE_OWNER);
		}
		menu.toggleSoldOut();
	}

	@Transactional
	public void createMenu(String email, Long storeId, MenuCreateCommand command) {
		Store store = storeRepository.findByIdWithOwner(storeId)
				.orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));

		if (!store.isOwnedBy(email)) {
			throw new StoreException(StoreErrorCode.NOT_STORE_OWNER);
		}

		menuRepository.save(new Menu(store, command.name(), command.price(), command.description()));
	}
}
