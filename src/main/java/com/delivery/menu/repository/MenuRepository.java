package com.delivery.menu.repository;

import java.util.List;
import java.util.Optional;

import com.delivery.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT m FROM Menu m WHERE m.store.store_id = :storeId")
    List<Menu> findAllByStoreId(@Param("storeId") Long storeId);

    @Query("SELECT m FROM Menu m WHERE m.menu_id = :menuId AND m.store.store_id = :storeId")
    Optional<Menu> findByIdAndStoreId(@Param("menuId") Long menuId, @Param("storeId") Long storeId);

    @Query("SELECT m FROM Menu m JOIN FETCH m.store s JOIN FETCH s.owner WHERE m.menu_id = :menuId AND m.store.store_id = :storeId")
    Optional<Menu> findByIdAndStoreIdWithOwner(@Param("menuId") Long menuId, @Param("storeId") Long storeId);
}
