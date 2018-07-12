package com.example.shopping.web.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.shopping.data.json.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProductToStringConverter implements Converter<Product, String> {

	ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public String convert(Product source) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(source);
			return json;
		} catch (JsonProcessingException e) {
			return "{\"id\":\""+source.getId()+ "\",\"price\":\"" +source.getPrice()+"\"}";
		}
	}

}
