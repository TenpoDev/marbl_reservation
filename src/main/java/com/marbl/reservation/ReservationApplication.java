package com.marbl.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
@EntityScan(basePackages = {"com.marbl.reservation"})
public class ReservationApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ReservationApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ReservationApplication.class);
	}
}
