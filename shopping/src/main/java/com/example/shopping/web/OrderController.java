package com.example.shopping.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.shopping.data.json.Product;
import com.example.shopping.data.model.Order;
import com.example.shopping.service.OrderService;
import com.example.shopping.service.ProductService;
import com.example.shopping.web.dto.CheckoutItem;
import com.example.shopping.web.dto.OrderDTO;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;

	@GetMapping("/orders")
	public String orders(Model model) {
		List<Order> orders = orderService.getOrders();
		model.addAttribute("orders", orders);
		return "orders/list";
	}

	@GetMapping("/orders/create-form")
	public String createOrder(Model model) {
		OrderDTO dto = getNewOrderDto();
		model.addAttribute("orderDto", dto);
		return "orders/create";
	}

	@GetMapping("/orders/{id}")
	public String viewOrder(@PathVariable Long id, Model model) {
		Order order = orderService.getOrder(id);
		model.addAttribute("order", order);
		return "orders/show";
	}

	@PostMapping("/orders")
	public String checkout(@ModelAttribute OrderDTO dto, Model model) {
		Order order = populateOrderFromDto(dto);

		Order checkoutOrder = orderService.checkout(order);
		model.addAttribute("order", checkoutOrder);
		return "orders/show";
	}
	
	private OrderDTO getNewOrderDto() {
		OrderDTO dto = new OrderDTO();
		List<Product> products = productService.getProducts();
		List<CheckoutItem> items = new ArrayList<>();
		for (Product product : products) {
			CheckoutItem item = new CheckoutItem();
			item.setProduct(product);
			item.setQuantity(0);
			items.add(item);
		}
		dto.setItems(items);
		return dto;
	}

	private Order populateOrderFromDto(OrderDTO dto) {
		Order order = new Order();
		for (int i = 0; i < dto.getItems().size(); i++) {
			int noOfItems = dto.getItems().get(i).getQuantity();
			if (noOfItems > 0) {
				Product product = dto.getItems().get(i).getProduct();
				product = productService.getProduct(product.getId());
				order.getProductQuantities().put(product, noOfItems);
			} else {
				continue;
			}
		}
		return order;
	}

}
