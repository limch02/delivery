package com.delivery.menu.presentation.dto;

import com.delivery.menu.application.dto.MenuCreateCommand;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MenuCreateRequest(
        @NotBlank String name,
        @Min(0) int price,
        String description
) {
    public MenuCreateCommand toCommand() {
        return new MenuCreateCommand(name, price, description);
    }
}
