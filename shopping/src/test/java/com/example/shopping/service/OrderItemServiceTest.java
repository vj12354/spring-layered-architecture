package com.example.shopping.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shopping.ShoppingApplication;
import com.example.shopping.data.json.Product;
import com.example.shopping.data.model.Order;
import com.example.shopping.data.model.OrderItem;
import com.example.shopping.data.repo.OrderItemRepository;
import com.example.shopping.data.repo.OrderRepository;
import com.example.shopping.service.dummy.TestData;

@SpringBootTest(classes = { ShoppingApplication.class } )
public class OrderItemServiceTest {
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private OrderItemRepository orderItemRepo;
	
	@InjectMocks
	private OrderItemService orderItemService;
	private Order order;
	private Product product;
	
	@Before
	public void setup() {
        MockitoAnnotations.initMocks(this);
        product = TestData.getProducts().get(0);
		order = TestData.createOrderWithProducts(1, product);
        
        OrderItem item = new OrderItem();
        item.setId(1);
        item.setProductId(product.getId());
        item.setProductPrice(product.getPrice());
        item.setQuantity(1);
        
        when(orderItemRepo.save(
        		(OrderItem) any(OrderItem.class)
        		)).thenReturn(item);
//        when(orderRepository.save(
//        		    (Order) any(Order.class) ))
//        		.thenReturn(order);
	}
	
	@Test
	public void orderItem_isSavedCorrectly() {
		OrderItem item = orderItemService.save(new OrderItem());
		
		assertNotNull(item);
		assertEquals(1, item.getId());
		assertEquals(product.getId(), item.getProductId());
	}
	
	@Test
	public void correctTotal_isCalcualted_forEachOrderItem() {
		OrderItem item = orderItemService.save(new OrderItem());
        item.setProductPrice(100);
        item.setQuantity(20);
		assertEquals(2000, item.getTotalPrice());
	}
}
