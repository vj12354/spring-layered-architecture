package com.example.shopping.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.model.OrderPromotion;

public interface OrderPromotionRepository extends JpaRepository<OrderPromotion, Long> {

}
