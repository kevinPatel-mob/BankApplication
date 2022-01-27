package com.mob.casestudy.digitalbanking.dto;

import com.mob.casestudy.digitalbanking.compositekey.CustomerQuestions;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerSecurityQuestionsDto {



    private CustomerQuestions customerQuestions;

    private String securityQuestionAnswer;

    private String createdOn;

    private Customer customer;

    private SecurityQuestions securityQuestions;


}
