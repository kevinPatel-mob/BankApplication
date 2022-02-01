package com.mob.casestudy.digitalbanking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerSecurityQuestionsDto {
    private String securityQuestionId;
    private String securityQuestion;
    private String securityQuestionAnswer;
}
