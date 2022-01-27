package com.mob.casestudy.digitalbanking.validator;

import com.mob.casestudy.digitalbanking.dto.CustomerDto;
import com.mob.casestudy.digitalbanking.enums.Language;
import com.mob.casestudy.digitalbanking.exceptionresponse.InvalidEmailException;
import com.mob.casestudy.digitalbanking.exceptionresponse.InvalidLanguageException;
import com.mob.casestudy.digitalbanking.exceptionresponse.InvalidPhoneNumberException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component

public class CustomerDetailValidation {

    //TODO: Pass the dto and validate the respective fields
    public void phoneEmailLanguageValidation(CustomerDto customerDto) {

        if (!isPhoneValid(customerDto.getPhoneNumber())) {
            throw new InvalidPhoneNumberException();
        }

        if (!isEmailValid(customerDto.getEmail())) {
            throw new InvalidEmailException();
        }

        if (!isLanguageValid(customerDto.getPreferredLanguage().trim().toUpperCase())) {
            throw new InvalidLanguageException();
        }

    }

    //TODO: Try to retrieve the regex from application.yml
    public boolean isEmailValid(String email) {
        /**
         final Pattern EMAIL_REGEX =
         Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", Pattern.CASE_INSENSITIVE);
         return EMAIL_REGEX.matcher(email).matches();
         Pattern pattern = Pattern.compile(regex);
         **/
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return email.matches(regex);
    }

    //TODO: Try to retrieve the regex from application.yml
    public boolean isPhoneValid(String number) {

        return number.matches("^\\d{10}$");
    }

    public boolean isLanguageValid(String language) {
        //TODO: Optimize the condition. Use the enum method for validation
        return    Arrays.stream(Language.values()).map(Enum::toString)
                .filter(e -> e.equalsIgnoreCase(language)).findAny().isPresent();
    }
}
