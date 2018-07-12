package com.example.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopping.model.OrderPromotion;
import com.example.shopping.repo.OrderPromotionRepository;

@Service
public class OrderPromotionService {
	
	@Autowired
	private OrderPromotionRepository orderPromotionRepository;
	
	public OrderPromotion save(OrderPromotion item) {
		return orderPromotionRepository.save(item);
	}
}
