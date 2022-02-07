package com.mob.casestudy.digitalbanking.validator;

import com.mob.casestudy.digitalbanking.configuration.RegexValues;
import com.mob.casestudy.digitalbanking.dto.CustomerDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.enums.CustomerStatus;
import com.mob.casestudy.digitalbanking.exceptionresponse.InvalidDataException;
import com.mob.casestudy.digitalbanking.requestbody.CustomerSecurityImageRequestBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;


@ExtendWith(MockitoExtension.class)
class CustomerDetailValidationTest {

    @InjectMocks
    CustomerDetailValidation customerDetailValidation;
    @Mock
    RegexValues regexValues;


    @Test
    void phone_Email_Language_Validation_If_Email_Is_Invalid_Throw_EmailInvalid_Exception() {

        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142000gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage("EN")
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();

        CustomerDto customerDto = customer.toDto();
        String value="^\\d{10}$";
        Mockito.when(regexValues.getPhoneRegex()).thenReturn(value);
        String emailValue=  "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Mockito.when(regexValues.getEmailRegex()).thenReturn(emailValue);
        Assertions.assertThrows(InvalidDataException.class,
                () -> customerDetailValidation.phoneEmailLanguageValidation(customerDto));
    }

    @Test
    void phone_Email_Language_Validation_If_Phone_Is_InValid_Throw_PhoneInvalid_Exception() {

        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("966484759").email("kevinpatel1142000gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage("EN")
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();

        CustomerDto customerDto = customer.toDto();
        String value="^\\d{10}$";
        Mockito.when(regexValues.getPhoneRegex()).thenReturn(value);
        Assertions.assertThrows(InvalidDataException.class,
                () -> customerDetailValidation.phoneEmailLanguageValidation(customerDto));
    }

    @Test
    void phone_Email_Language_Validation_If_Language_Is_InValid_Throw_LanguageInvalid_Exception() {
        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142000@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage("E")
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();

        CustomerDto customerDto = customer.toDto();
        String value="^\\d{10}$";
        Mockito.when(regexValues.getPhoneRegex()).thenReturn(value);
        String emailValue=  "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Mockito.when(regexValues.getEmailRegex()).thenReturn(emailValue);
        Assertions.assertThrows(InvalidDataException.class,
                () -> customerDetailValidation.phoneEmailLanguageValidation(customerDto));
    }


    @Test
    void isEmailValid_ForValidEmail_ReturnsTrue() {

        String email = "kevinpatel1142000@gmail.com";
        String value=  "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Mockito.when(regexValues.getEmailRegex()).thenReturn(value);
        Assertions.assertTrue(customerDetailValidation.isEmailValid(email));
    }

    @Test
    void isEmailValid_ForInvalidEmail_ReturnsFalse() {

        String email = "kevinpatel1142000gmail.com";
        Mockito.when(regexValues.getEmailRegex()).thenReturn( "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Assertions.assertFalse(customerDetailValidation.isEmailValid(email));

    }

    @Test
    void isPhoneValid_ForValidPhoneNumber_ReturnsTrue() {
        String number = "9664847593";
        String value="^\\d{10}$";
        Mockito.when(regexValues.getPhoneRegex()).thenReturn(value);
        Assertions.assertTrue(customerDetailValidation.isPhoneValid(number));
    }

    @Test
    void isPhoneValid_ForInvalidPhoneNumber_ReturnsFalse() {
        String number = "9664847q39";
        String value="^\\d{10}$";
        Mockito.when(regexValues.getPhoneRegex()).thenReturn(value);
        Assertions.assertFalse(customerDetailValidation.isPhoneValid(number));
    }

    @Test
    void isLanguageValid_ForInvalidLanguage_Input_ReturnFalse() {
        String language = "Ea";
        Assertions.assertFalse(customerDetailValidation.isLanguageValid(language));
    }

    @Test
    void isLanguageValid_ForValidLanguage_Input_ReturnTrue() {
        String language = "EN";
        Assertions.assertTrue(customerDetailValidation.isLanguageValid(language));
    }

    @Test
    void validateCustomerImageCaption_When_ImageID_Is_Null_Throw_InvalidDataException() {
        CustomerSecurityImageRequestBody customer=CustomerSecurityImageRequestBody.builder().securityImageCaption("testing")
                .securityImageId(null).build();
        Assertions.assertThrows(InvalidDataException.class,
                ()->customerDetailValidation.validateCustomerImageCaption(customer));
    }
    @Test
    void validateCustomerImageCaption_When_ImageCaption_Is_Null_Throw_InvalidDataException() {
        CustomerSecurityImageRequestBody customer=CustomerSecurityImageRequestBody.builder().securityImageCaption(null)
                .securityImageId("null").build();
        Assertions.assertThrows(InvalidDataException.class,
                ()->customerDetailValidation.validateCustomerImageCaption(customer));
    }
    @Test
    void validateCustomerImageCaption_When_ImageCaption_Is_Less_Then_3Character_Null_Throw_InvalidDataException() {
        CustomerSecurityImageRequestBody customer=CustomerSecurityImageRequestBody.builder().securityImageCaption("ke")
                .securityImageId("null").build();
        Assertions.assertThrows(InvalidDataException.class,
                ()->customerDetailValidation.validateCustomerImageCaption(customer));
    }

}