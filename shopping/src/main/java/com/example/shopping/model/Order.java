package com.example.shopping.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.example.shopping.config.AppConfig;
import com.example.shopping.model.json.Product;

@Entity
@Table(name="ordeer")
public class Order {

	@Id @GeneratedValue
	private long id;
	
	@Transient
	private Map<Product, Integer> productQuantities;

	@OneToMany(mappedBy="order")
	private List<OrderItem> orderItems;

	@OneToMany(mappedBy="order")
	private List<OrderPromotion> orderPromotions;
	
	private int totalNetPayable;
	private Date checkoutTimestamp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Map<Product, Integer> getProductQuantities() {
		if (productQuantities == null) {
			productQuantities = new LinkedHashMap<>();
		}
		return productQuantities;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public List<OrderPromotion> getOrderPromotions() {
		return orderPromotions;
	}

	public void setOrderPromotions(List<OrderPromotion> orderPromotions) {
		this.orderPromotions = orderPromotions;
	}

	public int getTotalNetPayable() {
		return totalNetPayable;
	}

	public void setTotalNetPayable(int totalNetPayable) {
		this.totalNetPayable = totalNetPayable;
	}

	public Date getCheckoutTimestamp() {
		return checkoutTimestamp;
	}

	public void setCheckoutTimestamp(Date checkoutTimestamp) {
		this.checkoutTimestamp = checkoutTimestamp;
	}

	public String getTotalPreDiscount() {
		int total = 0;
		if (orderItems != null)
		for (OrderItem item : orderItems) {
			total += item.getTotalPrice();
		}
		return AppConfig.getFormatted(total);
	}
	
	public String getTotalDiscount() {
		int total = 0;
		if (orderPromotions != null)
		for (OrderPromotion promotion : orderPromotions) {
			total += promotion.getPromotionValue();
		}
		return AppConfig.getFormatted(total);
	}
	
}
