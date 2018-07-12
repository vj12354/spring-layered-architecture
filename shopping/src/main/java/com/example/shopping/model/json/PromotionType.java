package com.example.shopping.model.json;

import java.math.BigDecimal;

import com.example.shopping.model.Order;
import com.example.shopping.model.OrderPromotion;

public enum PromotionType {
	BUY_X_GET_Y_FREE {
		@Override
		protected int calculatePriceDiscount(Product product, int quantity, Promotion promo) {
			int required = promo.getRequiredQty();
			int freeItems = promo.getFreeQty();
			if (quantity < required) {
				return 0;
			} else if (quantity > required) {
				return product.getPrice() * (quantity/required) * freeItems;
			} else {
				return product.getPrice() * freeItems;
			}
		}
	},
	
	QTY_BASED_PRICE_OVERRIDE {
		@Override
		protected int calculatePriceDiscount(Product product, int quantity, Promotion promo) {
			int requied = promo.getRequiredQty();
			if (quantity < requied) {
				return 0; 
			} else if (quantity > requied) {
				int noOfCoupens = quantity/requied;
				int totalCostForEligibleQuantity = product.getPrice() * requied * noOfCoupens;
				int offerCost = promo.getPrice() * noOfCoupens;
				return totalCostForEligibleQuantity - offerCost ;
			} else {
				return (product.getPrice() * requied) - promo.getPrice();
			}
		}
	},
	
	FLAT_PERCENT {
		@Override
		protected int calculatePriceDiscount(Product product, int quantity, Promotion promo) {
			BigDecimal dec = new BigDecimal(String.valueOf(promo.getAmount()));
			BigDecimal percent = dec.divide(new BigDecimal("100"));
			int total = product.getPrice() * quantity;
			return percent.multiply(new BigDecimal(total)).intValue();
		}
	};

	public OrderPromotion getOrderPromotion(Order order, Product product, Promotion promo) {
		if (promo == null) {
			return null;
		}
		int quantity = order.getProductQuantities().get(product);
		int discountedPrice = calculatePriceDiscount(product, quantity, promo);
		if (discountedPrice < 1) {
			return null;
		}
		return populateOrderPromotion(order, promo, discountedPrice);
	}

	protected int calculatePriceDiscount(Product product, int quantity, Promotion promo) {
		return 0;
	}

	private OrderPromotion populateOrderPromotion(Order order, Promotion promo, int discountedPrice) {
		OrderPromotion op = new OrderPromotion();
		op.setOrder(order);
		op.setPromotionId(promo.getId());
		op.setPromotionType(promo.getType().name());
		op.setPromotionValue(discountedPrice);
		return op;
	}
}
