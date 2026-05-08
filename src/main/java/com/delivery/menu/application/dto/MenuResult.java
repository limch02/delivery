package com.delivery.menu.application.dto;

import com.delivery.menu.domain.Menu;

public record MenuResult(
        Long menuId,
        String name,
        int price,
        String description,
        boolean soldOut
) {
    public static MenuResult from(Menu menu) {
        return new MenuResult(
                menu.getMenu_id(),
                menu.getName(),
                menu.getPrice(),
                menu.getDescription(),
                menu.isSoldOut()
        );
    }
}
