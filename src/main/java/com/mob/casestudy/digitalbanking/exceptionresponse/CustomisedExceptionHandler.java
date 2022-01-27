package com.mob.casestudy.digitalbanking.exceptionresponse;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.mob.casestudy.digitalbanking.errorcodes.CustomisedErrorCodesAndDescription.*;


@ControllerAdvice
public class CustomisedExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest webRequest) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), "Generic exception representating internal server error");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUser(UserNotFoundException exception, WebRequest webRequest) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(USER_NOT_FOUND, USER_NOT_FOUND_DESCRIPTION);


        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPhoneNumberException.class)
    public final ResponseEntity<Object> validatePhone() {

        ExceptionResponse exceptionResponse = new ExceptionResponse(INVALID_PHONE_NUMBER, INVALID_PHONE_NUMBER_DESCRIPTION);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public final ResponseEntity<Object> validateEmail() {

        ExceptionResponse exceptionResponse = new ExceptionResponse(INVALID_EMAIL, INVALID_EMAIL_DESCRIPTION);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(InvalidLanguageException.class)
    public final ResponseEntity<Object> validateLanguage() {

        ExceptionResponse exceptionResponse = new ExceptionResponse(INVALID_LANGUAGE_INPUT, INVALID_LANGUAGE_INPUT_DESCRIPTION);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MandatoryFieldException.class)
    public final ResponseEntity<Object> mandatoryFields() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(MANDATORY_FIELD, MANDATORY_FIELD_DESCRIPTION);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(QuestionEmptyException.class)
    public final ResponseEntity<Object> securityQuestionNotAvailable() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(SECURITY_QUESTION_NOT_IN_TABLE, SECURITY_QUESTION_NOT_IN_TABLE_DESCRIPTION);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(MANDATORY_FIELD, MANDATORY_FIELD_DESCRIPTION);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
