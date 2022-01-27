package com.mob.casestudy.digitalbanking.service;


import com.mob.casestudy.digitalbanking.dto.SecurityQuestionsDto;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.exceptionresponse.QuestionEmptyException;
import com.mob.casestudy.digitalbanking.repository.SecurityQuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityQuestionsService {

    @Autowired
    SecurityQuestionsRepository securityQuestionsRepository;

    public void addSecurityQuestions(){


        securityQuestionsRepository.save(SecurityQuestions.builder().securityQuestionText("What is Your Favourite Car").build());
        securityQuestionsRepository.save(SecurityQuestions.builder().securityQuestionText("What is Your  ChildHood name").build());
        securityQuestionsRepository.save(SecurityQuestions.builder().securityQuestionText("What is Your School name").build());
        securityQuestionsRepository.save(SecurityQuestions.builder().securityQuestionText("What is Your Dream Company").build());



    }

    public List<SecurityQuestionsDto> retrieveAllQuestions() {
        List<SecurityQuestions> securityQuestionsList = securityQuestionsRepository.findAll();
        if (securityQuestionsList.isEmpty()){
           throw  new QuestionEmptyException();
        }

        List<SecurityQuestionsDto> securityQuestionsDto=securityQuestionsList.stream().map(SecurityQuestions::toDto).toList();


        return securityQuestionsDto;
    }


}
