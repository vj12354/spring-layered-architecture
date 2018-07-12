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
import com.example.shopping.data.model.Order;
import com.example.shopping.data.model.OrderPromotion;
import com.example.shopping.data.repo.OrderPromotionRepository;
import com.example.shopping.data.repo.OrderRepository;

@SpringBootTest(classes = { ShoppingApplication.class } )
public class OrderPromotionServiceTest {
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private OrderPromotionRepository orderPromoRepo;
	
	@InjectMocks
	private OrderPromotionService orderPromotionService;
	
	@Before
	public void setup() {
        MockitoAnnotations.initMocks(this);
        Order order = new Order();
        order.setId(10);
        
        OrderPromotion item = new OrderPromotion();
        item.setId(1);
        item.setOrder(order);
        item.setPromotionId("test_id");
        item.setPromotionValue(40);
        
        when(orderPromoRepo.save(
        		(OrderPromotion) any(OrderPromotion.class)
        		)).thenReturn(item);
        
        when(orderRepository.save(
        		    (Order) any(Order.class) ))
        		.thenReturn(order);
	}
	
	@Test
	public void orderPromotion_isSavedCorrectly() {
		OrderPromotion promotion = orderPromotionService.save(new OrderPromotion());
		
		assertNotNull(promotion);
		assertNotNull(promotion.getOrder());
		assertEquals(1, promotion.getId());
		assertEquals("test_id", promotion.getPromotionId());
		assertEquals(40, promotion.getPromotionValue());
	}
	
//	@Test
//	public void correctTotal_isCalcualted_forEachOrderItem() {
//		OrderPromotion item = orderPromotionService.save(new OrderPromotion());
//        item.setProductPrice(100);
//        item.setQuantity(20);
//		assertEquals(2000, item.getTotalPrice());
//	}
}
