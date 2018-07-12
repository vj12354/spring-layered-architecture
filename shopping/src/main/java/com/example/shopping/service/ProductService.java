package com.example.shopping.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.shopping.model.json.Product;

@Service
public class ProductService {
	
	public static final String BASE_URL = "http://localhost:8081";
	public static final String URI_PRODUCTS = "/products";
	
	@Autowired
	private RestTemplate restTemplate;

	public List<Product> getProducts() {
		Product[] ps = restTemplate.getForEntity(BASE_URL+URI_PRODUCTS, Product[].class).getBody();
		return Arrays.asList(ps);
	}

	public Product getProduct(String id) {
		ResponseEntity<Product> resp =  restTemplate.getForEntity(BASE_URL+URI_PRODUCTS+"/"+id, Product.class);
		return resp.getBody();
	}

}

