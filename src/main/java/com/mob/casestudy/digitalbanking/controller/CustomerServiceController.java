package com.mob.casestudy.digitalbanking.controller;


import com.mob.casestudy.digitalbanking.dto.CustomerDto;
import com.mob.casestudy.digitalbanking.dto.GetAllSecurityQuestionDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.exceptionresponse.UserNotFoundException;
import com.mob.casestudy.digitalbanking.repository.CustomerRepository;
import com.mob.casestudy.digitalbanking.service.CustomerService;
import com.mob.casestudy.digitalbanking.service.SecurityQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/customer-service")
public class CustomerServiceController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final SecurityQuestionsService securityQuestionsService;

    @Autowired
    public CustomerServiceController(CustomerRepository customerRepository, CustomerService customerService, SecurityQuestionsService securityQuestionsService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.securityQuestionsService = securityQuestionsService;
    }

    @GetMapping(path = "/client-api/v1/customers")
    public ResponseEntity<CustomerDto> getCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        CustomerDto customerDto = customerList.get(0).toDto();
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @GetMapping(path = "/client-api/v1/customers/{name}")
    public ResponseEntity<CustomerDto> findByName(@PathVariable String name) {
        Optional<Customer> customerResult = customerRepository.findByUserName(name);

        if (!customerResult.isEmpty()) {

            CustomerDto customerDto = customerResult.get().toDto();
            return ResponseEntity.status(HttpStatus.OK).body(customerDto);
        } else {
            throw new UserNotFoundException();
        }
    }

    @PatchMapping(path = "/client-api/v1/customers/{username}", produces = "application/json")
    public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable String username) {
        customerService.updateCustomer(username, customerDto);
        return new ResponseEntity<>("User Updated:" + username, HttpStatus.OK);
    }

    @GetMapping(path = "/client-api/v1/securityQuestions", produces = "application/json")
    public ResponseEntity<Object> getAllSecurityQuestions() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetAllSecurityQuestionDto(securityQuestionsService.retrieveAllQuestions()));
    }

}
