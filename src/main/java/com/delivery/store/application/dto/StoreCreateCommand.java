package com.delivery.store.application.dto;

import java.time.LocalTime;

public record StoreCreateCommand(
        String name,
        String category,
        int minOrderPrice,
        int deliveryFee,
        String address,
        String phone,
        LocalTime openTime,
        LocalTime closeTime
) {
}
