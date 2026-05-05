package com.delivery.menu.domain;

import com.delivery.menu.exception.MenuErrorCode;
import com.delivery.menu.exception.MenuException;
import com.delivery.store.domain.Store;

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
@Table(name = "menus")
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id", nullable = false, updatable = false)
	private Long menu_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "price", nullable = false)
	private int price;

	@Column(name = "description")
	private String description;

	@Column(name = "sold_out", nullable = false)
	private boolean soldOut = false;

	public Menu(Store store, String name, int price, String description) {
		this.store = store;
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public void update(String name, int price, String description) {
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public boolean validateOwner(String email) {
		return this.store.getOwner().getEmail().equals(email);
	}

	public void toggleSoldOut() {
		this.soldOut = !this.soldOut;
	}
}
