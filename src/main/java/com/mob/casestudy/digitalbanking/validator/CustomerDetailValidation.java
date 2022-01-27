package com.mob.casestudy.digitalbanking.validator;

import com.mob.casestudy.digitalbanking.enums.Language;
import com.mob.casestudy.digitalbanking.exceptionresponse.InvalidEmailException;
import com.mob.casestudy.digitalbanking.exceptionresponse.InvalidLanguageException;
import com.mob.casestudy.digitalbanking.exceptionresponse.InvalidPhoneNumberException;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component

public class CustomerDetailValidation {

    //TODO: Pass the dto and validate the respective fields
    public void phoneEmailLanguageValidation(String phone, String email, String language) {

        if (!isPhoneValid(phone)) {
            throw new InvalidPhoneNumberException();
        }

        if (!isEmailValid(email)) {
            throw new InvalidEmailException();
        }

        if (!isLanguageValid(language)) {
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
        return language.equalsIgnoreCase(Language.EN.name())
                || language.equalsIgnoreCase(Language.DE.toString())
                || language.equalsIgnoreCase(Language.FR.toString());

    }
}
