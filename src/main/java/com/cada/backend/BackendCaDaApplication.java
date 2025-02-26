package com.cada.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
public class BackendCaDaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendCaDaApplication.class, args);
		System.out.println("Backend CaDa Application is running...");
		
	}

}
