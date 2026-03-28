package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"com.example.demo",
		"config",
		"controller",
		"dto",
		"service"
})
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "entity")
public class TicketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketsApplication.class, args);
		System.out.println("Tickets Application started successfully!");
	}

}
