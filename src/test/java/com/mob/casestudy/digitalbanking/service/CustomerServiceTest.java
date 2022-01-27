package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.dto.CustomerDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.enums.CustomerStatus;
import com.mob.casestudy.digitalbanking.enums.Language;
import com.mob.casestudy.digitalbanking.exceptionresponse.*;
import com.mob.casestudy.digitalbanking.repository.CustomerRepository;
import com.mob.casestudy.digitalbanking.validator.CustomerDetailValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;
    @Mock
    CustomisedExceptionHandler customisedExceptionHandler;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerDetailValidation customerDetailValidation;


    @Test
    void saveCustomer() {

        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage(Language.EN.toString())
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();
        customerService.saveCustomer(customer);
        Mockito.verify(customerRepository).save(customer);
    }


    @Test
    void updateCustomer_Return_Nothing() {
        String userName = "kep";
        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage("EN")
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();
        CustomerDto customerDto = customer.toDto();

        Mockito.when(customerRepository.findByUserName(userName)).thenReturn(Optional.of(customer));
        customerService.updateCustomer(userName, customerDto);

        Mockito.verify(customerRepository).save(customer);
    }

    @Test
    void validateCustomer_If_Customer_Is_Available() {
        String userName = "kep";
        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage("EN")
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();

        Mockito.when(customerRepository.findByUserName(userName)).thenReturn(Optional.of(customer));
        Customer expectedResult = customerService.validateCustomer(userName);
        Assertions.assertEquals(expectedResult, customer);
    }

    @Test
    void validateCustomer_If_Customer_Is_Not_Available_Throw_UserNotFoundException() {
        String userName = "kep";
        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage("EN")
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();

        Mockito.when(customerRepository.findByUserName(userName)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,
                () -> customerService.validateCustomer(userName));
    }

    @Test
    void mapToEntity() {
        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage("EN")
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();

        CustomerDto customerDto = customer.toDto();

        Customer expectedResult = customerService.mapDtoToEntity(customerDto, customer);
        Assertions.assertEquals(expectedResult, customer);
    }

    @Test
    void validateUserNameAndReturnCustomer() {
        String userName = "kep";

        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage("EN")
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();

        CustomerDto customerDto = customer.toDto();
        Mockito.when(customerRepository.findByUserName(userName)).thenReturn(Optional.of(customer));
        Customer expectedResult = customerService.validateUserNameAndReturnCustomer(userName, customerDto);

        Mockito.verify(customerDetailValidation)
                .phoneEmailLanguageValidation(customerDto);
        Assertions.assertEquals(expectedResult, customer);

    }


}
