package com.sg.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CarLoanApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarLoanApplication.class, args);
	}

}
