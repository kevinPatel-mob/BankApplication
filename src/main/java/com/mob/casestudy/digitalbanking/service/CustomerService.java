package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.compositekey.CustomerImage;
import com.mob.casestudy.digitalbanking.dto.CustomerDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import com.mob.casestudy.digitalbanking.enums.CustomerStatus;
import com.mob.casestudy.digitalbanking.exceptionresponse.*;
import com.mob.casestudy.digitalbanking.repository.CustomerRepository;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityImageRepository;
import com.mob.casestudy.digitalbanking.repository.SecurityImageRepository;
import com.mob.casestudy.digitalbanking.validator.CustomerDetailValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDetailValidation customerDetailValidation;
    private final SecurityImageRepository securityImageRepository;
    private final CustomerSecurityImageRepository customerSecurityImageRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerDetailValidation customerDetailValidation, SecurityImageRepository securityImageRepository, CustomerSecurityImageRepository customerSecurityImageRepository) {
        this.customerRepository = customerRepository;
        this.customerDetailValidation = customerDetailValidation;
        this.securityImageRepository = securityImageRepository;
        this.customerSecurityImageRepository = customerSecurityImageRepository;
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }


    public void saveCustomerSecurityImage(Customer customer){
        SecurityImages securityImages=SecurityImages.builder()
                .securityImageName("DSLR").securityImageUrl("Google.com").build();
        securityImageRepository.save(securityImages);

        customerRepository.save(customer);

        CustomerSecurityImages customerSecurityImages=CustomerSecurityImages.builder().customerImage(new CustomerImage()).createdOn("ToDay")
                .securityImageCaption("Nothing").securityImages(securityImages).customer(customer).build();
        customerSecurityImageRepository.save(customerSecurityImages);
    }


    public void updateCustomer(String userName, CustomerDto customerDto) {
        Customer customer = validateUserNameAndReturnCustomer(userName, customerDto);
        customerRepository.save(customer);
    }

    public Customer validateUserNameAndReturnCustomer(String userName, CustomerDto customerDto) {
        Customer customerResult = validateCustomer(userName);
        customerDetailValidation.
                phoneEmailLanguageValidation(customerDto);

        return mapDtoToEntity(customerDto, customerResult);
    }


    public Customer validateCustomer(String userName) {
        Optional<Customer> customerResultOptional = customerRepository.findByUserName(userName);
        if (!customerResultOptional.isPresent()) {
            throw new UserNotFoundException();
        }
        return customerResultOptional.get();
    }

    public void validateFieldAndPassToDto(CustomerDto customerDto){


    }

    //TODO: handle the null check
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
