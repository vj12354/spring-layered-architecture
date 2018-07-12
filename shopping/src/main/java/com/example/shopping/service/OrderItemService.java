package com.example.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopping.data.model.OrderItem;
import com.example.shopping.data.repo.OrderItemRepository;

@Service
public class OrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public OrderItem save(OrderItem item) {
		return orderItemRepository.save(item);
	}
}
