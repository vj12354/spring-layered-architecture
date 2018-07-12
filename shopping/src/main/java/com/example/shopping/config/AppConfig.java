package com.example.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

	public static String getFormatted(int price) {
		float val = new Float(price);
		String formatted = String.format(java.util.Locale.UK, "£%.2f", val/100f);
		return formatted;
	}
}
