package com.example.shopping.model.json;

public class Product {
	private String id;
	private String name;
	private int price;
	
	private Promotion[] promotions;
	
	public Product() {
		this(null, null, 0);
	}
	
	public Product(String id, String name, int price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public Promotion[] getPromotions() {
		return promotions;
	}

	public void setPromotions(Promotion[] promotions) {
		this.promotions = promotions;
	}
}
