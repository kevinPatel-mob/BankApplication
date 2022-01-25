package com.mob.casestudy.digitalbanking.controller;

import com.mob.casestudy.digitalbanking.dto.CustomerDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.repository.CustomerRepository;
import com.mob.casestudy.digitalbanking.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
class CustomerServiceControllerTest {


    @InjectMocks
    CustomerServiceController customerServiceController;

    @Mock
    CustomerService customerService;



    @Test
    void updateCustomer_Data_And_Response_Ok() {

        String customerName="kevin";

        CustomerDto customerDto=new CustomerDto();
        Customer customer=new Customer();

        ResponseEntity responseEntity = customerServiceController.updateCustomer(customerDto, customerName);
        ResponseEntity expectedResponse=new ResponseEntity<>("User Updated:"+customerName, HttpStatus.OK);
        Assertions.assertThat(expectedResponse).usingRecursiveComparison().isEqualTo(responseEntity);
    }
}