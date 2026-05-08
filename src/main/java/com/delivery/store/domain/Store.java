package com.delivery.store.domain;

import java.time.LocalTime;

import com.delivery.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "stores")
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "store_id", nullable = false, updatable = false)
	private Long store_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member owner;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "category", nullable = false, length = 50)
	private String category;

	@Column(name = "min_order_price", nullable = false)
	private int minOrderPrice;

	@Column(name = "delivery_fee", nullable = false)
	private int deliveryFee;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "phone", nullable = false, length = 13)
	private String phone;

	@Column(name = "open_time", nullable = false)
	private LocalTime openTime;

	@Column(name = "close_time", nullable = false)
	private LocalTime closeTime;

	public Store(Member owner, String name, String category, int minOrderPrice, int deliveryFee,
			String address, String phone, LocalTime openTime, LocalTime closeTime) {
		this.owner = owner;
		this.name = name;
		this.category = category;
		this.minOrderPrice = minOrderPrice;
		this.deliveryFee = deliveryFee;
		this.address = address;
		this.phone = phone;
		this.openTime = openTime;
		this.closeTime = closeTime;
	}

	public boolean isOwnedBy(String email) {
		return this.owner.getEmail().equals(email);
	}

	public void update(String name, String category, int minOrderPrice, int deliveryFee,
			String address, String phone, LocalTime openTime, LocalTime closeTime) {
		this.name = name;
		this.category = category;
		this.minOrderPrice = minOrderPrice;
		this.deliveryFee = deliveryFee;
		this.address = address;
		this.phone = phone;
		this.openTime = openTime;
		this.closeTime = closeTime;
	}
}
