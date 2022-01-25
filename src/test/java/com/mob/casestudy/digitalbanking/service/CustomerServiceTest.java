package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.dto.CustomerDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.enums.CustomerStatus;
import com.mob.casestudy.digitalbanking.enums.Language;
import com.mob.casestudy.digitalbanking.exceptionResponse.*;
import com.mob.casestudy.digitalbanking.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;
    @Mock
    CustomisedExceptionHandler customisedExceptionHandler;
    @Mock
    CustomerRepository customerRepository;


    @Test
    void validateUserNameAndReturnCustomer_If_FieldAre_Not_Inserted_ThenReturn_MandatoryFieldException()
    {

      //  CustomerDto customerDto=new CustomerDto();
        String userName="";
        Assertions.assertThrows(MandatoryFieldException.class,
            ()->customerService.validateUserNameAndReturnCustomer(userName,null));
    }

    @Test
    void validateUserNameAndReturnCustomer_If_Customer_Result_IsEmpty(){
        CustomerDto customerDto=new CustomerDto();
        String userName="kevin";
        Mockito.when(customerRepository.findByUserName(userName)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class,
                ()->customerService.validateUserNameAndReturnCustomer(userName, customerDto));
    }

    @Test
    void validateUserNameAndReturnCustomer_If_Customer_PhoneNumber_Is_Invalid_Throw_InvalidPhoneException(){

        String userName="kevin";
        Customer customer=Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("966484759").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage(Language.EN.toString())
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();


        CustomerDto customerDto = customer.toDto();

        Mockito.when(customerRepository.findByUserName(userName)).thenReturn(Optional.of(customer));
        Assertions.assertThrows(InvalidPhoneNumberException.class,
                ()->customerService.validateUserNameAndReturnCustomer(userName,customerDto));

    }
    @Test
    void validateUserNameAndReturnCustomer_If_Customer_Email_Is_Invalid_Throw_InvalidEmailException(){
        String userName="kevin";
        Customer customer=Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage(Language.EN.toString())
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();

        CustomerDto customerDto = customer.toDto();

        Mockito.when(customerRepository.findByUserName(userName)).thenReturn(Optional.of(customer));
        Assertions.assertThrows(InvalidEmailException.class,
                ()->customerService.validateUserNameAndReturnCustomer(userName,customerDto));


    }

    @Test
    void validateUserNameAndReturnCustomer_If_Customer_Language_Is_Invalid_Throw_InvalidLanguageException(){
        String userName="kevin";
        Customer customer=Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage("E")
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();


        CustomerDto customerDto = customer.toDto();
        Mockito.when(customerRepository.findByUserName(userName)).thenReturn(Optional.of(customer));
        Assertions.assertThrows(InvalidLanguageException.class,
                ()->customerService.validateUserNameAndReturnCustomer(userName,customerDto));
    }

    @Test
    void ValidateUserNameAndReturnCustomer_If_All_Validation_Is_True(){
        String userName="kevin";
        Customer customer=Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage("EN")
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();

        CustomerDto customerDto = customer.toDto();
        Mockito.when(customerRepository.findByUserName(userName)).thenReturn(Optional.of(customer));
        Customer expectedResult = customerService.validateUserNameAndReturnCustomer(userName, customerDto);
        Assertions.assertEquals(expectedResult,customer);


    }


    @Test
    void isEmailValid_ForValidEmail_ReturnsTrue() {

        String email="kevinpatel1142000@gmail.com";

       assertTrue(CustomerService.isEmailValid(email));
    }
    @Test
    void isEmailValid_ForInvalidEmail_ReturnsFalse() {

        String email="kevinpatel1142000gmail.com";

        assertFalse(CustomerService.isEmailValid(email));
    }

    @Test
    void isPhoneValid_ForValidPhoneNumber_ReturnsTrue() {
        String number="9664847593";
        assertTrue(customerService.isPhoneValid(number));
    }
    @Test
    void isPhoneValid_ForInvalidPhoneNumber_ReturnsFalse() {
        String number="9664847q39";
        assertFalse(customerService.isPhoneValid(number));
    }

    @Test
    void isLanguageValid_ForInvalidLanguage_Input_ReturnFalse(){
        String language="Ea";
        assertFalse(customerService.isLanguageValid(language));
    }

    @Test
    void isLanguageValid_ForValidLanguage_Input_ReturnTrue(){
        String language="EN";
        assertTrue(customerService.isLanguageValid(language));
    }
}