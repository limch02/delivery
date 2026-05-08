package com.delivery.store.presentation.dto;

import java.time.LocalTime;

import com.delivery.store.application.dto.StoreResult;

public record StoreResponse(
        String name,
        String category,
        int minOrderPrice,
        int deliveryFee,
        String address,
        String phone,
        LocalTime openTime,
        LocalTime closeTime
) {
    public static StoreResponse from(StoreResult result) {
        return new StoreResponse(
                result.name(),
                result.category(),
                result.minOrderPrice(),
                result.deliveryFee(),
                result.address(),
                result.phone(),
                result.openTime(),
                result.closeTime()
        );
    }
}
