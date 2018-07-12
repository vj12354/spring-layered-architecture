package com.example.shopping.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shopping.ShoppingApplication;
import com.example.shopping.model.Order;
import com.example.shopping.repo.OrderRepository;
import com.example.shopping.service.dummy.TestData;

@SpringBootTest(classes = { ShoppingApplication.class } )
public class OrderServiceTest {
	@Mock
	private OrderRepository orderRepository;
	
	@InjectMocks
	private OrderService orderService;
	private Order order;
	
	@Before
	public void setup() {
        MockitoAnnotations.initMocks(this);
        order = TestData.createOrderWithProducts(0, null);
        order.setId(10);
        
        
        when(orderRepository.save( (Order) any(Order.class) ))
        		.thenReturn(order);

        when(orderRepository.findById( (Long) any(Long.class) ))
        		.thenReturn(Optional.of(order));

        List<Order> orders = new ArrayList<>();
        orders.add(order);
        order = TestData.createOrderWithProducts(0, null);
        order.setId(20);
        orders.add(order);
        when(orderRepository.findAll()).thenReturn(orders);
	}
	
	@Test
	public void saveOrder() {
		Order order = orderService.save(new Order());
		assertNotNull(order);
		assertEquals(10, order.getId());
	}

	@Test
	public void getOrders_retrives_fromRepo() {
		Order o2 = orderService.getOrder(order.getId());
		assertNotNull(o2);
		assertEquals(10, o2.getId());
	}

	@Test
	public void getOrder_callesRepoTo_retrivesAOrder() {
		List<Order> orders = orderService.getOrders();
		assertNotNull(orders);
		assertEquals(2, orders.size());
		assertNotNull(orders.get(0));
		assertEquals(10, orders.get(0).getId());
		assertNotNull(orders.get(1));
		assertEquals(20, orders.get(1).getId());
	}

}
