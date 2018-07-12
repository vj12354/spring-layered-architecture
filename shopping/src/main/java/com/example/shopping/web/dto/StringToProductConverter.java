package com.example.shopping.web.dto;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.shopping.data.json.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class StringToProductConverter implements Converter<String,Product> {

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public Product convert(String source) {
		try {
			return mapper.readValue(source, Product.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
