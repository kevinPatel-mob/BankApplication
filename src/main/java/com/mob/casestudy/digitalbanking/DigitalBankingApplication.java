package com.mob.casestudy.digitalbanking;

import com.mob.casestudy.digitalbanking.service.CustomerService;
import com.mob.casestudy.digitalbanking.service.SecurityQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DigitalBankingApplication implements CommandLineRunner {


    @Autowired
    private CustomerService customerService;
    @Autowired
    private SecurityQuestionsService securityQuestionsService;

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApplication.class, args);
    }

	//TODO: Refactor all the package name from camel case to lower case
	//TODO: Convert application.properties to application.yml
    @Override
    public void run(String... args) throws Exception {
        customerService.saveCustomer();
        securityQuestionsService.addSecurityQuestions();
    }
}
