package com.example.shopping.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.example.shopping.model.json.Product;
import com.example.shopping.model.json.PromotionType;
import com.example.shopping.service.dummy.TestData;

public class ProductPromotionTypeTest {
	
	@Test
	public void buy1get1free_generatesCorrect_orderPromotions() {
		Product product = getBuyXGetYFreeProduct(2500, 1, 2);
		
		Order order = createOrderWithProductItems(2, product);
		OrderPromotion promo = getOrderPromotion(PromotionType.BUY_X_GET_Y_FREE, order, product);
		
		assertEquals(2500*1, promo.getPromotionValue());
	}
	
	@Test
	public void buy1get1free_generatesCorrect_orderPromotions_excess() {
		Product product = getBuyXGetYFreeProduct(2500, 1, 2);
		
		Order order = createOrderWithProductItems(7, product);
		OrderPromotion promo = getOrderPromotion(PromotionType.BUY_X_GET_Y_FREE, order, product);
		
		assertEquals(2500*3, promo.getPromotionValue());
	}
	
	private OrderPromotion getOrderPromotion(PromotionType type , Order order, Product product) {
		return type.getOrderPromotion(order, product, product.getPromotions()[0]);
	}
	
	@Test
	public void buy1get1free_generatesCorrect_orderPromotions_excessAndFree() {
		Product product = getBuyXGetYFreeProduct(1500, 2, 5);
		
		Order order = createOrderWithProductItems(7, product);
		OrderPromotion promo = getOrderPromotion(PromotionType.BUY_X_GET_Y_FREE, order, product);
		assertEquals(1500*2, promo.getPromotionValue());
		
		order = createOrderWithProductItems(10, product);
		promo = getOrderPromotion(PromotionType.BUY_X_GET_Y_FREE, order, product);
		assertEquals(1500*4, promo.getPromotionValue());
	}
	
	// QTY_BASED_PRICE_OVERRIDE
	@Test
	public void qtyBasedPriceOverride_noOrderPromotionsGenerated_whenNotEligible() {
		Product product = getQtyBasedPriceOverrideProduct(1300, 3000, 3);
		
		Order order = createOrderWithProductItems(2, product);
		OrderPromotion promo = getOrderPromotion(PromotionType.QTY_BASED_PRICE_OVERRIDE, order, product);
		
		assertNull(promo);
	}
	
	@Test
	public void qtyBasedPriceOverride_generatesCorrect_orderPromotions() {
		Product product = getQtyBasedPriceOverrideProduct(1300, 2000, 2);
		
		Order order = createOrderWithProductItems(2, product);
		OrderPromotion promo = getOrderPromotion(PromotionType.QTY_BASED_PRICE_OVERRIDE, order, product);
		
		assertEquals( ((1300*2) - 2000), promo.getPromotionValue());
	}
	
	@Test
	public void qtyBasedPriceOverride_generatesCorrect_orderPromotions_excess() {
		Product product = getQtyBasedPriceOverrideProduct(1300, 2000, 2);
		
		Order order = createOrderWithProductItems(7, product);
		OrderPromotion promo = getOrderPromotion(PromotionType.QTY_BASED_PRICE_OVERRIDE, order, product);
		
		assertEquals( ((1300*6) - (2000*3)), promo.getPromotionValue());
	}
	
	@Test
	public void qtyBasedPriceOverride_generatesCorrect_orderPromotions_excessAndFree() {
		Product product = getQtyBasedPriceOverrideProduct(500, 2100, 5);
		
		Order order = createOrderWithProductItems(17, product);
		OrderPromotion promo = getOrderPromotion(PromotionType.QTY_BASED_PRICE_OVERRIDE, order, product);
		assertEquals( ((500*15) - (2100*3)), promo.getPromotionValue());
		
		order = createOrderWithProductItems(35, product);
		promo = getOrderPromotion(PromotionType.QTY_BASED_PRICE_OVERRIDE, order, product);
		assertEquals( ((500*35) - (2100*7)), promo.getPromotionValue());
	}

	private Order createOrderWithProductItems(int noOfItems, Product product) {
		Order order = TestData.createOrderWithProducts(noOfItems, product);
//		for (OrderItem item : order.getOrderItems()) {
//			item.setProductId(product.getId());
//			item.setProductPrice(product.getPrice());
//		}
		return order;
	}

	@Test
	public void flatPercent_generatesCorrect_orderPromotions() {
		Product product = getFlatPercentProduct(1000, 10);
		
		Order order = createOrderWithProductItems(1, product);
		OrderPromotion promo = getOrderPromotion(PromotionType.FLAT_PERCENT, order, product);
		
		assertEquals(100, promo.getPromotionValue());
	}
	
	@Test
	public void flatPercent_generatesCorrect_orderPromotions_excess() {
		Product product = getFlatPercentProduct(1200, 25);
		
		Order order = createOrderWithProductItems(9, product);
		OrderPromotion promo = getOrderPromotion(PromotionType.FLAT_PERCENT, order, product);
		
		assertEquals(9*1200*.25, promo.getPromotionValue(), .01);
	}

	private Product getBuyXGetYFreeProduct(int price, int freeQty, int requiredQty) {
		Product product = TestData.getProductWithPromotion(price, PromotionType.BUY_X_GET_Y_FREE);
		product.getPromotions()[0].setFreeQty(freeQty);
		product.getPromotions()[0].setRequiredQty(requiredQty);
		return product;
	}
	
	private Product getQtyBasedPriceOverrideProduct(int price, int offerPrice, int requiredQty) {
		Product product = TestData.getProductWithPromotion(price, PromotionType.QTY_BASED_PRICE_OVERRIDE);
		product.getPromotions()[0].setPrice(offerPrice);
		product.getPromotions()[0].setRequiredQty(requiredQty);
		return product;
	}

	private Product getFlatPercentProduct(int price, int percent) {
		Product product = TestData.getProductWithPromotion(price, PromotionType.FLAT_PERCENT);
		product.getPromotions()[0].setAmount(percent);
		return product;
	}

}
