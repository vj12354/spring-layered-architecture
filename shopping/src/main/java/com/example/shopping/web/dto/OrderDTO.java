package com.example.shopping.web.dto;

import java.util.List;

public class OrderDTO {
	private List<CheckoutItem> items;

	public List<CheckoutItem> getItems() {
		return items;
	}

	public void setItems(List<CheckoutItem> items) {
		this.items = items;
	}
}
