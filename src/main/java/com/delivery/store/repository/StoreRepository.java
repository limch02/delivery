package com.delivery.store.repository;

import java.util.Optional;

import com.delivery.store.domain.Store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

	@Query("SELECT s FROM Store s JOIN FETCH s.owner WHERE s.store_id = :storeId")
	Optional<Store> findByIdWithOwner(@Param("storeId") Long storeId);
}

