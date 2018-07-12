package com.example.shopping.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.shopping.ShoppingApplication;
import com.example.shopping.config.AppConfig;
import com.example.shopping.model.json.Product;
import com.example.shopping.service.dummy.TestData;

@SpringBootTest(classes = { ShoppingApplication.class } )
public class ProductServiceTest {
	
	@Mock 
	private RestTemplate restTemplate;
	
	@InjectMocks
	private ProductService productService;
	
	@SuppressWarnings("rawtypes")
	@Before
	public void setup() {
        MockitoAnnotations.initMocks(this);
        	String url = ProductService.BASE_URL+ ProductService.URI_PRODUCTS;
        Product[] products = TestData.getProducts().toArray(new Product[] {});
        
		when(restTemplate.getForEntity(
        		url, Product[].class))
        		.thenReturn( new ResponseEntity<Product[]>(products, HttpStatus.OK));
		
		for (Product product : products) {
			when(restTemplate.getForEntity(
	        		url+"/"+ product.getId(), Product.class))
	        		.thenReturn(new ResponseEntity<Product>(product, HttpStatus.OK));
		}
	}
	
	@Test
	public void getProduct_wouldCall_restServerURL() {
		List<Product> products = productService.getProducts();
		assertNotNull("products is null", products);
	}
	
	@Test
	public void getProducts_returnsListOfProducts() {
		List<Product> products = productService.getProducts();
		assertNotNull("products is null", products);
		assertEquals(TestData.getProducts().size(), products.size());
	}

	@Test
	public void getProduct_returnsProductDetails() {
		Product expectedProd = TestData.getProducts().get(0);
		Product actualProd = productService.getProduct(expectedProd.getId());
		assertNotNull("product is null", actualProd);
		assertEquals(expectedProd, actualProd);
	}
	
	@Test
	public void formatedPrice() {
		Product product = TestData.getProductPromotional();
		product.setPrice(2389);
		product.getPromotions()[0].setPrice(12345);
		assertEquals("£23.89", AppConfig.getFormatted(product.getPrice()));
		assertEquals("£123.45", AppConfig.getFormatted(
				product.getPromotions()[0].getPrice()));
	}
}
