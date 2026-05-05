package com.delivery.menu.presentation.dto;

import com.delivery.menu.application.dto.MenuUpdateCommand;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MenuUpdateRequest(
        @NotBlank String name,
        @Min(0) int price,
        String description
) {
    public MenuUpdateCommand toCommand() {
        return new MenuUpdateCommand(name, price, description);
    }
}
