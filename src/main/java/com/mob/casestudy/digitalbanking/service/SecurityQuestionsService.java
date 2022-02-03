package com.mob.casestudy.digitalbanking.service;


import com.mob.casestudy.digitalbanking.compositekey.CustomerQuestions;
import com.mob.casestudy.digitalbanking.dto.CustomerSecurityQuestionsDto;
import com.mob.casestudy.digitalbanking.dto.SecurityQuestionsDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityQuestions;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.exceptionresponse.DataNotFoundException;
import com.mob.casestudy.digitalbanking.repository.CustomerRepository;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityQuestionRepository;
import com.mob.casestudy.digitalbanking.repository.SecurityQuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.mob.casestudy.digitalbanking.errorcodes.CustomisedErrorCodesAndDescription.*;

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



    public List<SecurityQuestionsDto> retrieveAllQuestions() {
        List<SecurityQuestions> securityQuestionsList = securityQuestionsRepository.findAll();
        if (securityQuestionsList.isEmpty()) {
            throw new DataNotFoundException(SECURITY_QUESTION_NOT_IN_TABLE,SECURITY_QUESTION_NOT_IN_TABLE_DESCRIPTION);
        }
        return securityQuestionsList.stream().map(SecurityQuestions::toDto).toList();
    }

    public void addCustomerQuestionAnswer(Customer customer) {
        SecurityQuestions question1 = securityQuestionsRepository.save
                (SecurityQuestions.builder().securityQuestionText("What is Your Favourite Car").build());
        SecurityQuestions question2 = securityQuestionsRepository.save(SecurityQuestions.builder().securityQuestionText("What is Your  ChildHood name").build());
        SecurityQuestions question3 = securityQuestionsRepository.save(SecurityQuestions.builder().securityQuestionText("What is Your School name").build());

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
            throw new DataNotFoundException(CUSTOMER_NOT_IN_TABLE,CUSTOMER_NOT_IN_TABLE_DESCRIPTION);
        }
        return customerSecurityQuestionValidation(customerResult.get());
    }
    private List<CustomerSecurityQuestionsDto> customerSecurityQuestionValidation(Customer customerResult) {
        List<CustomerSecurityQuestions> customerSecurityQuestionsList = customerResult.getQuestionsList();
        if (Objects.isNull(customerSecurityQuestionsList) || customerSecurityQuestionsList.isEmpty()) {
            throw new DataNotFoundException(CUSTOMER_SECURITY_QUESTION_NOT_IN_TABLE,CUSTOMER_SECURITY_QUESTION_NOT_IN_TABLE_DESCRIPTION);
        }
        return customerSecurityQuestionsList.stream().map(CustomerSecurityQuestions::toDto).toList();
    }
}
