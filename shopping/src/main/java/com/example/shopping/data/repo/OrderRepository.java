package com.example.shopping.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.data.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
