package com.delivery.menu.presentation.dto;

import com.delivery.menu.application.dto.MenuResult;

public record MenuResponse(
        Long menuId,
        String name,
        int price,
        String description,
        boolean soldOut
) {
    public static MenuResponse from(MenuResult result) {
        return new MenuResponse(
                result.menuId(),
                result.name(),
                result.price(),
                result.description(),
                result.soldOut()
        );
    }
}
