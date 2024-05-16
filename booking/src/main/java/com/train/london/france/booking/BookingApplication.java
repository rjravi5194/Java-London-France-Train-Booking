package com.train.london.france.booking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Train Booking APIs", description = "APIs to book train ticket from London- Paris", version = "1.0.0"),
		tags = @Tag(name = "Train Booking APIs", description = "APIs to book train ticket from London- Paris"))
public class BookingApplication{

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

}
