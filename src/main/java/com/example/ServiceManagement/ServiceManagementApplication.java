package com.example.ServiceManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.ServiceManagement.model")
@EnableJpaRepositories("com.example.ServiceManagement.repository")
public class ServiceManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceManagementApplication.class, args);
	}
}
