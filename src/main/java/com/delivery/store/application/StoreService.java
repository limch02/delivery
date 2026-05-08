package com.delivery.store.application;

import java.util.List;

import com.delivery.member.domain.Member;
import com.delivery.member.exception.MemberErrorCode;
import com.delivery.member.exception.MemberException;
import com.delivery.member.repository.MemberRepository;
import com.delivery.store.application.dto.StoreCreateCommand;
import com.delivery.store.application.dto.StoreResult;
import com.delivery.store.application.dto.StoreUpdateCommand;
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
public class StoreService {

	private final StoreRepository storeRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void createStore(String email, StoreCreateCommand command) {
		Member owner = memberRepository.findByEmail(email)
				.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

		Store store = new Store(
				owner,
				command.name(),
				command.category(),
				command.minOrderPrice(),
				command.deliveryFee(),
				command.address(),
				command.phone(),
				command.openTime(),
				command.closeTime()
		);

		storeRepository.save(store);
	}

	public StoreResult getStore(Long storeId) {
		Store store = storeRepository.findById(storeId)
				.orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));
		return StoreResult.from(store);
	}

	public List<StoreResult> getAllStores() {
		return storeRepository.findAll()
				.stream()
				.map(StoreResult::from)
				.toList();
	}

	@Transactional
	public void updateStore(String email, Long storeId, StoreUpdateCommand command) {
		Store store = storeRepository.findByIdWithOwner(storeId)
				.orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));

		if (!store.isOwnedBy(email)) {
			throw new StoreException(StoreErrorCode.NOT_STORE_OWNER);
		}
		store.update(command.name(), command.category(), command.minOrderPrice(), command.deliveryFee(),
				command.address(), command.phone(), command.openTime(), command.closeTime());
	}

	@Transactional
	public void deleteStore(String email, Long storeId) {
		Store store = storeRepository.findByIdWithOwner(storeId)
				.orElseThrow(() -> new StoreException(StoreErrorCode.STORE_NOT_FOUND));

		if (!store.isOwnedBy(email)) {
			throw new StoreException(StoreErrorCode.NOT_STORE_OWNER);
		}
		storeRepository.delete(store);
	}
}
