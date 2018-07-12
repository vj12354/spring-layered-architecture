package com.example.shopping.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.shopping.ShoppingApplication;
import com.example.shopping.model.Order;
import com.example.shopping.model.OrderPromotion;
import com.example.shopping.model.json.Product;
import com.example.shopping.model.json.PromotionType;
import com.example.shopping.repo.OrderItemRepository;
import com.example.shopping.repo.OrderPromotionRepository;
import com.example.shopping.repo.OrderRepository;
import com.example.shopping.service.dummy.TestData;

@SpringBootTest(classes = { ShoppingApplication.class } )
public class OrderServiceCheckoutTest {

	@InjectMocks
	private OrderService orderService;
	private Order order;
	private Product productPromotional, productNonPromotional;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private OrderItemRepository itemRepository;
	@Mock
	private OrderPromotionRepository promotionRepository;
	
	@Before
	public void setup() {
        MockitoAnnotations.initMocks(this);
        order = TestData.createOrderWithProducts(0, null);
        productPromotional = TestData.getProductPromotional();
        productNonPromotional = TestData.getProductNonPromotional();

            MockitoAnnotations.initMocks(this);
            order = TestData.createOrderWithProducts(0, null);
            order.setId(10);
            
            
            when(orderRepository.save( (Order) any(Order.class) ))
            		.thenReturn(order);
	}

	@Test
	public void checkout_wouldCreate_correctOrderItems() {
		Order order = TestData.createOrderWithProducts(2, productPromotional);
		Order checkedoutOrder = orderService.checkout(order);
		assertNotNull(checkedoutOrder);
		assertEquals(1, checkedoutOrder.getOrderItems().size());
		assertEquals(checkedoutOrder.getOrderItems().get(0).getProductId(), productPromotional.getId());
		assertEquals(checkedoutOrder.getOrderItems().get(0).getProductPrice(), productPromotional.getPrice());
	}

	@Test
	public void checkout_nonPromotional_hasCorrectPaymentTotal() {
		Order order = TestData.createOrderWithProducts(2, productNonPromotional);
		Order checkedoutOrder = orderService.checkout(order);
		
		assertEquals(productNonPromotional.getPrice()*2, checkedoutOrder.getTotalNetPayable());
	}

	@Test
	public void checkout_promotional_hasCorrectPaymentTotal() {
		Order order = TestData.createOrderWithProducts(2, productPromotional);
		
		productPromotional.setPrice(1200);
		productPromotional.getPromotions()[0].setType(PromotionType.FLAT_PERCENT);
		productPromotional.getPromotions()[0].setAmount(50);
		
		Order checkedoutOrder = orderService.checkout(order);
		
		assertEquals(1200*0.5 *2, checkedoutOrder.getTotalNetPayable(), 0.1);
		assertEquals(1, checkedoutOrder.getOrderPromotions().size());
		assertEquals(1200*0.5*2, getOrderPromotion(checkedoutOrder, productPromotional).getPromotionValue(), 0.1);
	}
	
	@Test
	public void checkout_promotional_wouldCreateOrderPromotions() {
		Order order = TestData.createOrderWithProducts(2, productPromotional);	
		Order checkedoutOrder = orderService.checkout(order);
		
		assertEquals(1, checkedoutOrder.getOrderPromotions().size());
	}
	
	@Test
	public void checkout_nonPromotionItem_withCorrectNetTotal() {
        Order order = TestData.createOrderWithProducts(20, productNonPromotional);
        productNonPromotional.setPrice(1389);
        assertTrue(order.getOrderPromotions() == null || order.getOrderPromotions().size() == 0);
		
		Order completedOrder = orderService.checkout(order);
        assertTrue(completedOrder.getOrderPromotions() == null || completedOrder.getOrderPromotions().size() == 0);

        assertEquals(20*1389, completedOrder.getOrderItems().get(0).getTotalPrice());
        assertEquals(20*1389, completedOrder.getTotalNetPayable());
        assertNotNull(completedOrder.getCheckoutTimestamp());
	}
	
	@Test
	public void checkout_withPromotionItem_generatesPromotionValues() {
        Product product = TestData.getProductWithPromotion(1100, PromotionType.QTY_BASED_PRICE_OVERRIDE);
		product.getPromotions()[0].setPrice(1900);
		product.getPromotions()[0].setRequiredQty(3);
		
        assertNull(order.getOrderItems());

        Order order = TestData.createOrderWithProducts(5, product);
		
		Order checkedoutOrder = orderService.checkout(order);
		assertNotNull(checkedoutOrder);
        assertEquals(1, checkedoutOrder.getOrderItems().size());
        assertEquals(1, checkedoutOrder.getOrderPromotions().size());
        assertEquals( (1100*5) - (1900+1100+1100), 
        		getOrderPromotion(checkedoutOrder, product).getPromotionValue());
        assertEquals( (1100*5) - ((1100*5) - (1900+1100+1100)), order.getTotalNetPayable());
	}

	@Test
	public void checkout_handles_multipleProducts_nonPromotional() {
        Product product1 = TestData.getProductNonPromotional();
        product1.setId("product1");
        product1.setPrice(1100);

        Product product2 = TestData.getProductNonPromotional();
        product2.setId("product2");
        product2.setPrice(4390);

        Product product3 = TestData.getProductNonPromotional();
        product3.setId("product3");
		product3.setPrice(2500);

        Order order = TestData.createOrderWithProducts(1, product1);
        order.getProductQuantities().put(product2, 2);
        order.getProductQuantities().put(product3, 5);
        assertEquals(3, order.getProductQuantities().size());
		
		Order checkedoutOrder = orderService.checkout(order);
		assertNotNull(checkedoutOrder);
        assertEquals(3, checkedoutOrder.getOrderItems().size());
        assertEquals(0, checkedoutOrder.getOrderPromotions().size());
        
        assertEquals( (1100 *1) + (4390 *2) + (2500 *5), order.getTotalNetPayable());
	}

	@Test
	public void checkout_handles_multipleProducts_promotional() {
        Product product1 = TestData.getProductWithPromotion(1100, PromotionType.FLAT_PERCENT);
        product1.setId("product1");
        product1.getPromotions()[0].setId("promo1");
		product1.getPromotions()[0].setAmount(20);
		
        Product product2 = TestData.getProductWithPromotion(1500, PromotionType.BUY_X_GET_Y_FREE);
        product2.setId("product2");
        product2.getPromotions()[0].setId("promo2");
		product2.getPromotions()[0].setRequiredQty(3);
		product2.getPromotions()[0].setFreeQty(1);

        Product product3 = TestData.getProductNonPromotional();
        product3.setId("product3");
		product3.setPrice(4000);

        Order order = TestData.createOrderWithProducts(4, product1);
        order.getProductQuantities().put(product2, 5);
        order.getProductQuantities().put(product3, 3);
        assertEquals(3, order.getProductQuantities().size());
		
		Order checkedoutOrder = orderService.checkout(order);
		assertNotNull(checkedoutOrder);
        assertEquals(3, checkedoutOrder.getOrderItems().size());
        assertEquals(2, checkedoutOrder.getOrderPromotions().size());
        
        int discountOnProduct1 = new Float(1100 * .20 * 4).intValue() ;
		assertEquals( discountOnProduct1, 
        		getOrderPromotion(checkedoutOrder, product1).getPromotionValue());

        int discountOnProduct2 = 1500;
        assertEquals( discountOnProduct2, 
        		getOrderPromotion(checkedoutOrder, product2).getPromotionValue());
        
        assertEquals( (1100 *4) + (1500 *5) + (4000 *3) - discountOnProduct1 - discountOnProduct2, 
        		order.getTotalNetPayable());
	}	

	private OrderPromotion getOrderPromotion(Order checkedoutOrder, Product product1) {
		for (OrderPromotion op : checkedoutOrder.getOrderPromotions()) {
			if (op.getPromotionId().equals(product1.getPromotions()[0].getId())) {
				return op;
			}
		}
		return null;
	}

}
