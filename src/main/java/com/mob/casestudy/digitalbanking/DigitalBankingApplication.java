package com.mob.casestudy.digitalbanking;

import com.mob.casestudy.digitalbanking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DigitalBankingApplication implements CommandLineRunner {


	@Autowired
	private CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		customerService.saveCustomer();

	}
}
