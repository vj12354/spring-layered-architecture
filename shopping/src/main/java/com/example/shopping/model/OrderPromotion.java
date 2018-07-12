package com.example.shopping.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderPromotion {

	@Id @GeneratedValue
	private long id;
	
	private String promotionId;
	private String promotionType;
	private int promotionValue;
	
	@ManyToOne
	private Order order;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotion_id) {
		this.promotionId = promotion_id;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public int getPromotionValue() {
		return promotionValue;
	}

	public void setPromotionValue(int promotionValue) {
		this.promotionValue = promotionValue;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
