package com.delivery.menu.presentation;

import java.util.List;

import com.delivery.common.response.ApiResponse;
import com.delivery.menu.application.MenuService;
import com.delivery.menu.presentation.dto.MenuCreateRequest;
import com.delivery.menu.presentation.dto.MenuResponse;
import com.delivery.menu.presentation.dto.MenuUpdateRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores/{storeId}/menus")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<MenuResponse>>> getMenus(@PathVariable Long storeId) {
		List<MenuResponse> menus = menuService.getMenus(storeId)
				.stream()
				.map(MenuResponse::from)
				.toList();
		return ResponseEntity.ok(ApiResponse.success(menus));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createMenu(
			@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long storeId,
			@Valid @RequestBody MenuCreateRequest request
	) {
		menuService.createMenu(userDetails.getUsername(), storeId, request.toCommand());
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success());
	}

	@PatchMapping("/{menuId}")
	public ResponseEntity<ApiResponse<Void>> updateMenu(
			@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long storeId,
			@PathVariable Long menuId,
			@Valid @RequestBody MenuUpdateRequest request
	) {
		menuService.updateMenu(userDetails.getUsername(), storeId, menuId, request.toCommand());
		return ResponseEntity.ok(ApiResponse.success());
	}

	@DeleteMapping("/{menuId}")
	public ResponseEntity<ApiResponse<Void>> deleteMenu(
			@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long storeId,
			@PathVariable Long menuId
	) {
		menuService.deleteMenu(userDetails.getUsername(), storeId, menuId);
		return ResponseEntity.ok(ApiResponse.success());
	}

	@PatchMapping("/{menuId}/sold-out")
	public ResponseEntity<ApiResponse<Void>> markAsSoldOut(
			@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long storeId,
			@PathVariable Long menuId
	) {
		menuService.markAsSoldOut(userDetails.getUsername(), storeId, menuId);
		return ResponseEntity.ok(ApiResponse.success());
	}

	@PatchMapping("/{menuId}/in-stock")
	public ResponseEntity<ApiResponse<Void>> markAsAvailable(
			@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long storeId,
			@PathVariable Long menuId
	) {
		menuService.markAsAvailable(userDetails.getUsername(), storeId, menuId);
		return ResponseEntity.ok(ApiResponse.success());
	}
}
