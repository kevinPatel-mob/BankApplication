package com.mob.casestudy.digitalbanking.validator;

import com.mob.casestudy.digitalbanking.dto.CustomerDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.enums.CustomerStatus;
import com.mob.casestudy.digitalbanking.exceptionresponse.InvalidEmailException;
import com.mob.casestudy.digitalbanking.exceptionresponse.InvalidLanguageException;
import com.mob.casestudy.digitalbanking.exceptionresponse.InvalidPhoneNumberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;


@ExtendWith(MockitoExtension.class)
class CustomerDetailValidationTest {

    @InjectMocks
    CustomerDetailValidation customerDetailValidation;

    @Test
    void phone_Email_Language_Validation_If_Email_Is_Invalid_Throw_EmailInvalid_Exception() {

        Customer customer=Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142000gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage("EN")
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();

        CustomerDto customerDto = customer.toDto();

        Assertions.assertThrows(InvalidEmailException.class,
                ()-> customerDetailValidation.phoneEmailLanguageValidation(customerDto));
    }

    @Test
     void phone_Email_Language_Validation_If_Phone_Is_InValid_Throw_PhoneInvalid_Exception() {

        Customer customer=Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("966484759").email("kevinpatel1142000gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage("EN")
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();

        CustomerDto customerDto = customer.toDto();

        Assertions.assertThrows(InvalidPhoneNumberException.class,
                ()-> customerDetailValidation.phoneEmailLanguageValidation(customerDto));
    }
    @Test
     void phone_Email_Language_Validation_If_Language_Is_InValid_Throw_LanguageInvalid_Exception() {
        Customer customer=Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142000gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage("E")
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();

        CustomerDto customerDto = customer.toDto();
        Assertions.assertThrows(InvalidLanguageException.class,
                ()-> customerDetailValidation.phoneEmailLanguageValidation(customerDto));
    }




    @Test
    void isEmailValid_ForValidEmail_ReturnsTrue() {

        String email="kevinpatel1142000@gmail.com";

        Assertions.assertTrue(customerDetailValidation.isEmailValid(email));
    }
    @Test
    void isEmailValid_ForInvalidEmail_ReturnsFalse() {

        String email="kevinpatel1142000gmail.com";

        Assertions.assertFalse(customerDetailValidation.isEmailValid(email));

    }

    @Test
    void isPhoneValid_ForValidPhoneNumber_ReturnsTrue() {
        String number="9664847593";
        Assertions. assertTrue(customerDetailValidation.isPhoneValid(number));
    }
    @Test
    void isPhoneValid_ForInvalidPhoneNumber_ReturnsFalse() {
        String number="9664847q39";
        Assertions.assertFalse(customerDetailValidation.isPhoneValid(number));
    }

    @Test
    void isLanguageValid_ForInvalidLanguage_Input_ReturnFalse(){
        String language="Ea";
        Assertions.assertFalse(customerDetailValidation.isLanguageValid(language));
    }

    @Test
    void isLanguageValid_ForValidLanguage_Input_ReturnTrue(){
        String language="EN";
        Assertions.assertTrue(customerDetailValidation.isLanguageValid(language));
    }
}