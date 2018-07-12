package com.example.shopping.service.dummy;

import java.util.ArrayList;
import java.util.List;

import com.example.shopping.data.json.Product;
import com.example.shopping.data.json.Promotion;
import com.example.shopping.data.json.PromotionType;
import com.example.shopping.data.model.Order;
import com.example.shopping.data.model.OrderItem;

public class TestData {

	public static Order createOrderWithProducts(int noOfOrderItems, Product product) {
		Order order = new Order();
        order.setId(10);
        
        order.getProductQuantities().put(product, noOfOrderItems);
//        addOrderItems(noOfOrderItems, order, withPromotionProducts);
		return order;
	}
	
	public static List<Product> getProducts() {
		return DUMMY_PRODUCTS;
	}
	
	public static Product getProductNonPromotional() {
		return getProductWithPromotion(10, null);
	}
	
	public static Product getProductPromotional() {
		return getProductWithPromotion(10, PromotionType.FLAT_PERCENT);
	}

	/*private static void addOrderItems(int noOfOrderItems, Order order, boolean withPromotionProducts) {
		if (noOfOrderItems > 0) {
	        order.setOrderItems(new ArrayList<>());
	        for (int i = 1; i <= noOfOrderItems; i++) {
				OrderItem item = createOrderItem(order, withPromotionProducts, i);
				order.getOrderItems().add(item);
			}
        }
	}*/

	private static OrderItem createOrderItem(Order order, boolean withPromotionProducts, int i) {
		OrderItem item = new OrderItem();
		item.setId(i);
		item.setOrder(order);

		Product product = getAnyProduct(withPromotionProducts);
		item.setProductId(product.getId());
		item.setProductPrice(product.getPrice());
		return item;
	}
	
	private static Product getAnyProduct(boolean withPromotions) {
		for (Product product : DUMMY_PRODUCTS) {
			if (withPromotions) {
				if (product.getPromotions().length > 0) 
					return product;
				else 
					continue;
			} else {
				if (product.getPromotions() == null || product.getPromotions().length == 0) 
					return product;
				else 
					continue;
			}
		}
		return null;
	}

	private static int getRamdomPrice(int i) {
		Double seed = new Double(Math.random()+10);
		int price = seed.intValue()* (i+1);
		return price;
	}

	private static Promotion[] getPromotions(String promoid, PromotionType type, int promotionPrice) {
		List<Promotion> promotions = new ArrayList<>();
		
			Promotion promotion = new Promotion();
			promotion.setAmount(10);
			promotion.setFreeQty(2);
			promotion.setId(promoid);
			promotion.setPrice(promotionPrice);
			promotion.setRequiredQty(2);
			promotion.setType(type);
		promotions.add(promotion);
		return promotions.toArray(new Promotion[] {});
	}

	public static Product getProductWithPromotion(int price, PromotionType type) {
		Product p = new Product();
		p.setId("prodid");
		p.setName("aproduct");
		p.setPrice(price);
		
		if (type != null) {
			p.setPromotions(getPromotions("promoid", type, price/2));
		}
		return p;
	}

	private static List<Product> DUMMY_PRODUCTS;
	
	static {
		DUMMY_PRODUCTS = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			int price = getRamdomPrice(i);
				
			Product product = new Product("id"+i, "product"+i, price );	
			try {
				PromotionType type = PromotionType.values()[i];
				product.setPromotions(getPromotions("promoid"+i, type, price/2));
			} catch (ArrayIndexOutOfBoundsException e) {}
			
			DUMMY_PRODUCTS.add(product);
		}
	}
}
