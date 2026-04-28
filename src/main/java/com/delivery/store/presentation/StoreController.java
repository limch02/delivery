package com.delivery.store.presentation;

import java.util.List;

import com.delivery.common.response.ApiResponse;
import com.delivery.store.application.StoreService;
import com.delivery.store.presentation.dto.StoreCreateRequest;
import com.delivery.store.presentation.dto.StoreResponse;
import com.delivery.store.presentation.dto.StoreUpdateRequest;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createStore(
			@AuthenticationPrincipal UserDetails userDetails,
			@Valid @RequestBody StoreCreateRequest request
	) {
		storeService.createStore(userDetails.getUsername(), request.toCommand());
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success());
	}

	@GetMapping("/{storeId}")
	public ResponseEntity<ApiResponse<StoreResponse>> getStore(@PathVariable Long storeId) {
		return ResponseEntity.ok(ApiResponse.success(StoreResponse.from(storeService.getStore(storeId))));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<StoreResponse>>> getAllStores() {
		List<StoreResponse> stores = storeService.getAllStores()
				.stream()
				.map(StoreResponse::from)
				.toList();
		return ResponseEntity.ok(ApiResponse.success(stores));
	}

	@PatchMapping("/{storeId}")
	public ResponseEntity<ApiResponse<Void>> updateStore(
			@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long storeId,
			@Valid @RequestBody StoreUpdateRequest request
	) {
		storeService.updateStore(userDetails.getUsername(), storeId, request.toCommand());
		return ResponseEntity.ok(ApiResponse.success());
	}

	@DeleteMapping("/{storeId}")
	public ResponseEntity<ApiResponse<Void>> deleteStore(
			@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long storeId
	) {
		storeService.deleteStore(userDetails.getUsername(), storeId);
		return ResponseEntity.ok(ApiResponse.success());
	}
}
