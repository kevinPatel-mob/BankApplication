package com.mob.casestudy.digitalbanking.validator;

import com.mob.casestudy.digitalbanking.configuration.RegexValues;
import com.mob.casestudy.digitalbanking.dto.CustomerDto;
import com.mob.casestudy.digitalbanking.enums.Language;
import com.mob.casestudy.digitalbanking.exceptionresponse.InvalidEmailException;
import com.mob.casestudy.digitalbanking.exceptionresponse.InvalidLanguageException;
import com.mob.casestudy.digitalbanking.exceptionresponse.InvalidPhoneNumberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CustomerDetailValidation {


    @Autowired
    RegexValues regexValues;

    public void phoneEmailLanguageValidation(CustomerDto customerDto) {

        if (!isPhoneValid(customerDto.getPhoneNumber())) {
            throw new InvalidPhoneNumberException();
        }
        if ( !isEmailValid(customerDto.getEmail())) {
            throw new InvalidEmailException();
        }
        if (customerDto.getPreferredLanguage()!=null &&!isLanguageValid(customerDto.getPreferredLanguage())) {
            throw new InvalidLanguageException();
        }
    }

    public boolean isEmailValid(String email) {

        return email ==null ||  email.isEmpty() || email.matches(regexValues.getEmailRegex())  ;

    }

    public boolean isPhoneValid(String number) {
        return number ==null || number.isEmpty() ||  number.matches(regexValues.getPhoneRegex());
    }

    public boolean isLanguageValid(String language) {
        return  language.trim().isEmpty() || Arrays.stream(Language.values()).map(Enum::toString).anyMatch(l -> l.equalsIgnoreCase(language));
    }
}
