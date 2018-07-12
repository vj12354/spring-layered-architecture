package com.example.shopping.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
