package com.mob.casestudy.digitalbanking.service;


import com.mob.casestudy.digitalbanking.compositekey.CustomerQuestions;
import com.mob.casestudy.digitalbanking.dto.CustomerSecurityQuestionsDto;
import com.mob.casestudy.digitalbanking.dto.SecurityQuestionsDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityQuestions;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.enums.CustomerStatus;
import com.mob.casestudy.digitalbanking.enums.Language;
import com.mob.casestudy.digitalbanking.exceptionresponse.CustomerNotPresentException;
import com.mob.casestudy.digitalbanking.exceptionresponse.CustomerSecurityQuestionNotFountException;
import com.mob.casestudy.digitalbanking.exceptionresponse.QuestionEmptyException;
import com.mob.casestudy.digitalbanking.repository.CustomerRepository;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityQuestionRepository;
import com.mob.casestudy.digitalbanking.repository.SecurityQuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SecurityQuestionsService {

    private final SecurityQuestionsRepository securityQuestionsRepository;

    private final CustomerRepository customerRepository;
    private final CustomerSecurityQuestionRepository customerSecurityQuestionRepository;

    @Autowired
    public SecurityQuestionsService(SecurityQuestionsRepository securityQuestionsRepository, CustomerRepository customerRepository, CustomerSecurityQuestionRepository customerSecurityQuestionRepository) {
        this.securityQuestionsRepository = securityQuestionsRepository;
        this.customerRepository = customerRepository;
        this.customerSecurityQuestionRepository = customerSecurityQuestionRepository;
    }

    public void addSecurityQuestions() {
        securityQuestionsRepository.save(SecurityQuestions.builder().securityQuestionText("What is Your Favourite Car").build());
        securityQuestionsRepository.save(SecurityQuestions.builder().securityQuestionText("What is Your  ChildHood name").build());
        securityQuestionsRepository.save(SecurityQuestions.builder().securityQuestionText("What is Your School name").build());
        securityQuestionsRepository.save(SecurityQuestions.builder().securityQuestionText("What is Your Dream Company").build());
    }

    public List<SecurityQuestionsDto> retrieveAllQuestions() {
        List<SecurityQuestions> securityQuestionsList = securityQuestionsRepository.findAll();
        if (securityQuestionsList.isEmpty()) {
            throw new QuestionEmptyException();
        }
        return securityQuestionsList.stream().map(SecurityQuestions::toDto).toList();
    }

    public void addCustomerQuestionAnswer() {
        SecurityQuestions question1 = securityQuestionsRepository.save
                (SecurityQuestions.builder().securityQuestionText("What is Your Favourite Car").build());
        SecurityQuestions question2 = securityQuestionsRepository.save(SecurityQuestions.builder().securityQuestionText("What is Your  ChildHood name").build());
        SecurityQuestions question3 = securityQuestionsRepository.save(SecurityQuestions.builder().securityQuestionText("What is Your School name").build());
        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage(Language.EN.toString())
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();
        customerRepository.save(customer);
        CustomerSecurityQuestions customerSecurityQuestions1 = CustomerSecurityQuestions.builder().customer(customer).securityQuestions(question1)
                .createdOn("now").customerQuestions(new CustomerQuestions()).securityQuestionAnswer("BMW").build();
        CustomerSecurityQuestions customerSecurityQuestions2 = CustomerSecurityQuestions.builder().customer(customer).securityQuestions(question2)
                .createdOn("then").customerQuestions(new CustomerQuestions()).securityQuestionAnswer("kevin").build();
        CustomerSecurityQuestions customerSecurityQuestions3 = CustomerSecurityQuestions.builder().customer(customer).securityQuestions(question3)
                .createdOn("never").customerQuestions(new CustomerQuestions()).securityQuestionAnswer("navchettan").build();
        customerSecurityQuestionRepository.save(customerSecurityQuestions1);
        customerSecurityQuestionRepository.save(customerSecurityQuestions2);
        customerSecurityQuestionRepository.save(customerSecurityQuestions3);
    }

    public List<CustomerSecurityQuestionsDto> getSecurityQuestionAndAnswer(String userName) {
        Optional<Customer> customerResult = customerRepository.findByUserName(userName);
        if (customerResult.isEmpty()) {
            throw new CustomerNotPresentException();
        }
        return customerSecurityQuestionValidation(customerResult);
    }
    private List<CustomerSecurityQuestionsDto> customerSecurityQuestionValidation(Optional<Customer> customerResult) {
        List<CustomerSecurityQuestions> customerSecurityQuestionsList = customerResult.get().getQuestionsList();
        if (customerSecurityQuestionsList.isEmpty()) {
            throw new CustomerSecurityQuestionNotFountException();
        }
        return customerSecurityQuestionsList.stream().map(CustomerSecurityQuestions::toDto).toList();
    }
}
