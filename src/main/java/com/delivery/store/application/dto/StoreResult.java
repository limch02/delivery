package com.delivery.store.application.dto;

import java.time.LocalTime;

import com.delivery.store.domain.Store;

public record StoreResult(
        String name,
        String category,
        int minOrderPrice,
        int deliveryFee,
        String address,
        String phone,
        LocalTime openTime,
        LocalTime closeTime
) {
    public static StoreResult from(Store store) {
        return new StoreResult(
                store.getName(),
                store.getCategory(),
                store.getMinOrderPrice(),
                store.getDeliveryFee(),
                store.getAddress(),
                store.getPhone(),
                store.getOpenTime(),
                store.getCloseTime()
        );
    }
}
