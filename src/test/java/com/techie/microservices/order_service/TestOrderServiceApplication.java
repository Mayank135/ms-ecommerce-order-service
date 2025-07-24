package com.techie.microservices.order_service;

import com.techie.microservices.order_service.OrderServiceApplication;
import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

public class TestOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(OrderServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
