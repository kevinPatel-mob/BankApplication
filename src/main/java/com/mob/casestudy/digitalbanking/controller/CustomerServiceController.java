package com.mob.casestudy.digitalbanking.controller;


import com.mob.casestudy.digitalbanking.dto.CustomerDto;
import com.mob.casestudy.digitalbanking.dto.CustomerSecurityImagesDto;
import com.mob.casestudy.digitalbanking.dto.GetAllSecurityQuestionDto;
import com.mob.casestudy.digitalbanking.dto.GetCustomerSecurityQuestionsResponse;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.exceptionresponse.DataNotFoundException;
import com.mob.casestudy.digitalbanking.repository.CustomerRepository;
import com.mob.casestudy.digitalbanking.requestbody.CustomerSecurityImageRequestBody;
import com.mob.casestudy.digitalbanking.service.CustomerService;
import com.mob.casestudy.digitalbanking.service.SecurityImagesService;
import com.mob.casestudy.digitalbanking.service.SecurityQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.mob.casestudy.digitalbanking.errorcodes.CustomisedErrorCodesAndDescription.USER_NOT_FOUND;
import static com.mob.casestudy.digitalbanking.errorcodes.CustomisedErrorCodesAndDescription.USER_NOT_FOUND_DESCRIPTION;


@RestController
@RequestMapping(path = "/customer-service")
public class CustomerServiceController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final SecurityQuestionsService securityQuestionsService;
    private final SecurityImagesService securityImagesService;

    @Autowired
    public CustomerServiceController(CustomerRepository customerRepository, CustomerService customerService, SecurityQuestionsService securityQuestionsService, SecurityImagesService securityImagesService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.securityQuestionsService = securityQuestionsService;
        this.securityImagesService = securityImagesService;
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
            throw new DataNotFoundException(USER_NOT_FOUND, USER_NOT_FOUND_DESCRIPTION);
        }
    }

    @PatchMapping(path = "/client-api/v1/customers/{username}", produces = "application/json")
    public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable String username) {
        customerService.updateCustomer(username, customerDto);
        return new ResponseEntity<>("User Updated:" + username, HttpStatus.OK);
    }

    @PatchMapping(path = "/client-api/v1/customers/demo/{username}", produces = "application/json")
    public ResponseEntity<Object> updateCustomerDemo(@RequestBody CustomerDto customerDto, @PathVariable String username) {
        customerService.updateCustomerDemo(username, customerDto);
        return new ResponseEntity<>("User Updated:" + username, HttpStatus.OK);
    }

    @GetMapping(path = "/client-api/v1/securityQuestions", produces = "application/json")
    public ResponseEntity<Object> getAllSecurityQuestions() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetAllSecurityQuestionDto(securityQuestionsService.retrieveAllQuestions()));
    }

    @GetMapping(path = "/client-api/v1/customers/{username}/securityQuestions", produces = "application/json")
    public ResponseEntity<Object> getCustomerSecurityQuestions(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(new GetCustomerSecurityQuestionsResponse(securityQuestionsService.
                getSecurityQuestionAndAnswer(username)));
    }

    @GetMapping(path = "/client-api/v1/customers/{username}/securityImages", produces = "application/json")
    public ResponseEntity<CustomerSecurityImagesDto> retrieveCustomerSecurityImages(@PathVariable String username) {
        CustomerSecurityImagesDto customerSecurityImagesDto = securityImagesService.getSecurityImages(username);
        return ResponseEntity.status(HttpStatus.OK).body(customerSecurityImagesDto);
    }

    @PutMapping(path = "/client-api/v1/customers/{username}/securityImages", produces = "application/json")
    public ResponseEntity<String> updateCustomerSecurityImage(@PathVariable String username, @RequestBody CustomerSecurityImageRequestBody customerSecurityImageRequestBody) {
        securityImagesService.validateCustomerSecurityImageAndUpdate(username, customerSecurityImageRequestBody);
        return new ResponseEntity<>("Customer Image Updated :" + username, HttpStatus.OK);
    }

}
