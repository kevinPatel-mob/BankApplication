package com.mob.casestudy.digitalbanking.dto;

import com.mob.casestudy.digitalbanking.entity.CustomerSecurityQuestions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SecurityQuestionsDto {

    private String securityQuestionId ;

    private String securityQuestionText;


    private List<CustomerSecurityQuestions> questionsList=new ArrayList<>();
}
