package com.delivery.cart.application.dto;

public record CartAddCommand(Long menuId, int quantity) {
}
