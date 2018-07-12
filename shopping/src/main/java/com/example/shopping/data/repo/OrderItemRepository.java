package com.example.shopping.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.data.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
