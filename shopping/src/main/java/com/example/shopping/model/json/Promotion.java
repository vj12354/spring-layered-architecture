package com.example.shopping.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Promotion {
	private String id;
	private PromotionType type;
	
	@JsonProperty("required_qty")
	private int requiredQty;
	
	private int price;	
	@JsonProperty("free_qty")
	private int freeQty;
	
	private int amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PromotionType getType() {
		return type;
	}

	public void setType(PromotionType type) {
		this.type = type;
	}

	public int getRequiredQty() {
		return requiredQty;
	}

	public void setRequiredQty(int requiredQty) {
		this.requiredQty = requiredQty;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getFreeQty() {
		return freeQty;
	}

	public void setFreeQty(int freeQty) {
		this.freeQty = freeQty;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
