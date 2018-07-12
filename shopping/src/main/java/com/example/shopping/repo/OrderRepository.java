package com.example.shopping.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
