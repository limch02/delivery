package com.delivery.menu.application.dto;

public record MenuUpdateCommand(
        String name,
        int price,
        String description
) {
}
