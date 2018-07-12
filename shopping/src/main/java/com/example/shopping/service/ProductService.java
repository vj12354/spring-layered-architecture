package com.example.shopping.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.shopping.data.json.Product;

@Service
public class ProductService {
	
	@Value("${app.products.url}")
	private String productsURL;
	
	@Autowired
	private RestTemplate restTemplate;

	public List<Product> getProducts() {
		Product[] ps = restTemplate.getForEntity(productsURL, Product[].class).getBody();
		return Arrays.asList(ps);
	}

	public Product getProduct(String id) {
		ResponseEntity<Product> resp =  restTemplate.getForEntity(productsURL+"/"+id, Product.class);
		return resp.getBody();
	}

}

