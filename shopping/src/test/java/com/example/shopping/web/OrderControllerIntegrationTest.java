package com.example.shopping.web;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.example.shopping.ShoppingApplication;
import com.example.shopping.data.json.Product;
import com.example.shopping.service.OrderService;
import com.example.shopping.service.ProductService;
import com.example.shopping.service.ProductServiceTest;
import com.example.shopping.service.dummy.TestData;

@SpringBootTest(classes = { ShoppingApplication.class } )
public class OrderControllerIntegrationTest {

	private MockMvc mockMvc;
	@Mock
	private OrderService orderService;
	@Mock
	private ProductService productService;

	@Mock 
	private RestTemplate restTemplate;
	
	@InjectMocks
	private OrderController controller;
	
	@Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

	    	String url = ProductServiceTest.BASE_URL+ ProductServiceTest.URI_PRODUCTS;
	    List<Product> products = TestData.getProducts();
	    
		when(restTemplate.getForEntity(
	    		url, List.class))
	    		.thenReturn(new ResponseEntity<List>(products, HttpStatus.OK));
		
		for (Product product : products) {
			when(restTemplate.getForEntity(
	        		url+"/"+ product.getId(), Product.class))
	        		.thenReturn(new ResponseEntity<Product>(product, HttpStatus.OK));
		}
    } 
	
	@Test
	public void orders_wouldReturn_orderPage() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/orders"))
                .andExpect(forwardedUrl("orders/list"));
        verify(orderService, atLeast(1)).getOrders();
	}

	@Test
	public void createOrder_wouldReturn_formWith_productList() throws Exception {
        mockMvc.perform(get("/orders/create-form"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/orders/create-form"))
                .andExpect(forwardedUrl("orders/create"));
        verify(productService, atLeast(1)).getProducts();
	}

	
	@Test
	public void viewOrder_wouldShowDetails() throws Exception {
        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk());
        verify(orderService, atLeast(1)).getOrder(1);

        mockMvc.perform(get("/orders/1"))
                .andExpect(forwardedUrl("orders/show"));
	}
}
