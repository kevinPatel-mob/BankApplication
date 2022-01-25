package com.mob.casestudy.digitalbanking.validator;

import com.mob.casestudy.digitalbanking.exceptionResponse.CustomisedExceptionHandler;
import com.mob.casestudy.digitalbanking.exceptionResponse.InvalidEmailException;
import com.mob.casestudy.digitalbanking.exceptionResponse.InvalidLanguageException;
import com.mob.casestudy.digitalbanking.exceptionResponse.InvalidPhoneNumberException;
import com.mob.casestudy.digitalbanking.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CustomerDetailValidationTest {

    @InjectMocks
    CustomerDetailValidation customerDetailValidation;

    @Test
   public void phone_Email_Language_Validation_If_Email_Is_Invalid_Throw_EmailInvalid_Exception() {
        String email="kevinpatel1142000gmail.com";
        String number="9664847593";
        String language="EN";
        Assertions.assertThrows(InvalidEmailException.class,
                ()-> customerDetailValidation.phone_Email_Language_Validation(number,email,language));
    }

    @Test
    public void phone_Email_Language_Validation_If_Phone_Is_InValid_Throw_PhoneInvalid_Exception() {
        String email="kevinpatel1142000@gmail.com";
        String number="966484759";
        String language="EN";
        Assertions.assertThrows(InvalidPhoneNumberException.class,
                ()-> customerDetailValidation.phone_Email_Language_Validation(number,email,language));
    }
    @Test
    public void phone_Email_Language_Validation_If_Language_Is_InValid_Throw_LanguageInvalid_Exception() {
        String email="kevinpatel1142000@gmail.com";
        String number="9664847593";
        String language="E";
        Assertions.assertThrows(InvalidLanguageException.class,
                ()-> customerDetailValidation.phone_Email_Language_Validation(number,email,language));
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