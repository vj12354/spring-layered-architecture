package com.example.shopping.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopping.model.Order;
import com.example.shopping.model.OrderItem;
import com.example.shopping.model.OrderPromotion;
import com.example.shopping.model.json.Product;
import com.example.shopping.model.json.Promotion;
import com.example.shopping.repo.OrderItemRepository;
import com.example.shopping.repo.OrderPromotionRepository;
import com.example.shopping.repo.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository itemRepository;
	@Autowired
	private OrderPromotionRepository promotionRepository;
	
	@Transactional
	public Order save(Order order) {
		Order o = orderRepository.save(order);
		if (order.getOrderPromotions() != null && order.getOrderPromotions().size() > 0) {
			promotionRepository.saveAll(order.getOrderPromotions());
		}
		if (order.getOrderItems() != null && order.getOrderItems().size() > 0) {
			itemRepository.saveAll(order.getOrderItems());
		}
		return o;
	}
	
	@Transactional
	public List<Order> getOrders() {
		return orderRepository.findAll();
	}
	
	@Transactional
	public Order getOrder(long id) {
		return orderRepository.findById(id).get();
	}

	@Transactional
	public Order checkout(Order order) {
		List<OrderItem> orderItems = new ArrayList<>();
		List<OrderPromotion> orderPromotions = new ArrayList<>();
		int total = 0, promoDiscount = 0;
		
		for (Product	 product : order.getProductQuantities().keySet()) {
			OrderItem item = getOrderItem(order, product);
			total += item.getTotalPrice();
			
			orderItems.add(item);
			if (product.getPromotions() != null) { 
				for (Promotion promotion: product.getPromotions()) {
					OrderPromotion promo = promotion.getType().getOrderPromotion(order, product, promotion);
					if (promo == null) {
						continue;
					} else {
						orderPromotions.add(promo);
						promoDiscount += promo.getPromotionValue();
					}
				}
			}
//			promoDiscount += getPromotionalDiscount(order, orderPromotions, promoDiscount, product);
		}
		order.setOrderItems(orderItems);
		order.setOrderPromotions(orderPromotions);
		order.setTotalNetPayable(total - promoDiscount);
		order.setCheckoutTimestamp(new Date());
		save(order);
		return order;
	}

	private OrderItem getOrderItem(Order order, Product product) {
		OrderItem item = new OrderItem();
		item.setOrder(order);
		item.setProductId(product.getId());
		item.setProductName(product.getName());
		item.setProductPrice(product.getPrice());
		item.setQuantity(order.getProductQuantities().get(product));
		return item;
	}
}
