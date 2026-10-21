package com.kapitelweise.kapitelweiseBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

//unser main class, der die Spring Boot Anwendung startet
@SpringBootApplication
@EnableScheduling
public class KapitelweiseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(KapitelweiseBackendApplication.class, args);
	}
}
