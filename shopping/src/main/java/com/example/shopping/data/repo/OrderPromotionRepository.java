package com.example.shopping.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.data.model.OrderPromotion;

public interface OrderPromotionRepository extends JpaRepository<OrderPromotion, Long> {

}
