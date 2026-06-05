package com.delivery.order.domain;

public enum OrderStatus {
	PENDING, ACCEPTED, DELIVERING, DONE;

	public boolean hasNext() {
		return this != DONE;
	}

	public OrderStatus next() {
		return switch (this) {
			case PENDING -> ACCEPTED;
			case ACCEPTED -> DELIVERING;
			case DELIVERING -> DONE;
			case DONE -> throw new IllegalStateException("마지막 상태입니다.");
		};
	}
}
