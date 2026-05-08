package com.delivery.menu.application.dto;

public record MenuCreateCommand(
        String name,
        int price,
        String description
) {
}
