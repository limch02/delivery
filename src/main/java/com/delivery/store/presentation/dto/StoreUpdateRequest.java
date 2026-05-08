package com.delivery.store.presentation.dto;

import java.time.LocalTime;

import com.delivery.store.application.dto.StoreUpdateCommand;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StoreUpdateRequest(
        @NotBlank String name,
        @NotBlank String category,
        @Min(0) int minOrderPrice,
        @Min(0) int deliveryFee,
        @NotBlank String address,
        @NotBlank String phone,
        @NotNull LocalTime openTime,
        @NotNull LocalTime closeTime
) {
    public StoreUpdateCommand toCommand() {
        return new StoreUpdateCommand(name, category, minOrderPrice, deliveryFee, address, phone, openTime, closeTime);
    }
}
