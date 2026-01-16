package com.example.apistudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.apistudent.repository")
public class ApistudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApistudentApplication.class, args);
	}

}
