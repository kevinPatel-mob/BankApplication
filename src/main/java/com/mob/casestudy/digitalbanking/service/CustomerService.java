package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.dto.CustomerDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.enums.CustomerStatus;
import com.mob.casestudy.digitalbanking.enums.Language;
import com.mob.casestudy.digitalbanking.exceptionresponse.*;
import com.mob.casestudy.digitalbanking.repository.CustomerRepository;
import com.mob.casestudy.digitalbanking.validator.CustomerDetailValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDetailValidation customerDetailValidation;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerDetailValidation customerDetailValidation) {
        this.customerRepository = customerRepository;
        this.customerDetailValidation = customerDetailValidation;
    }

    public void saveCustomer() {

        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage(Language.EN.toString())
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();
        customerRepository.save(customer);
    }


    public void updateCustomer(String userName, CustomerDto customerDto) {
        Customer customer = validateUserNameAndReturnCustomer(userName, customerDto);
        customerRepository.save(customer);
    }

    public Customer validateUserNameAndReturnCustomer(String userName, CustomerDto customerDto) {
        Customer customerResult = validateCustomer(userName);
        //TODO : Refactor the below method calls
        customerDetailValidation.
                phoneEmailLanguageValidation(customerDto);

        return mapDtoToEntity(customerDto, customerResult);
    }


    public Customer validateCustomer(String userName) {
        Optional<Customer> customerResultOptional = customerRepository.findByUserName(userName);
        //TODO : User isPresent method for optional
        if (!customerResultOptional.isPresent()) {
            throw new UserNotFoundException();
        }
        return customerResultOptional.get();
    }

    //TODO: Use the builder annotation of lombok
    //TODO: Set only the fields which are provided in request
    public Customer mapDtoToEntity(CustomerDto customerDto, Customer customerResult) {
        customerResult = customerResult.withFirstName(customerDto.getFirstName())
                .withLastName(customerDto.getLastName())
                .withPhoneNumber(customerDto.getPhoneNumber())
                .withEmail(customerDto.getEmail())
                .withPreferredLanguage(customerDto.getPreferredLanguage())
                .withStatus(CustomerStatus.valueOf(customerDto.getStatus()));

        return customerResult;
    }


}
