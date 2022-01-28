package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.dto.SecurityQuestionsDto;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.exceptionresponse.QuestionEmptyException;
import com.mob.casestudy.digitalbanking.repository.SecurityQuestionsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityQuestionsServiceTest {

    @InjectMocks
    SecurityQuestionsService securityQuestionsService;

    @Mock
    SecurityQuestionsRepository securityQuestionsRepository;
    @Test
    void retrieveAllQuestions_If_Questions_Empty_Throw_QuestionEmptyException() {
        List<SecurityQuestions> questionsList=new ArrayList<>();
        Mockito.when(securityQuestionsRepository.findAll()).thenReturn(questionsList);
        Assertions.assertThrows(QuestionEmptyException.class
                ,()->securityQuestionsService.retrieveAllQuestions());
    }

    @Test
    void retrieveAllQuestions_If_Questions_Available_Return_SecurityQuestionDto() {
        SecurityQuestions question1= SecurityQuestions
                .builder().securityQuestionId(String.valueOf(UUID.randomUUID()))
                .securityQuestionText("What is Your Favourite Car").build();
        SecurityQuestions question2= SecurityQuestions
                .builder().securityQuestionId(String.valueOf(UUID.randomUUID())).
                securityQuestionText("What is Your  ChildHood name").build();
        List<SecurityQuestions> questionsList=new ArrayList<>();
        questionsList.add(question1);
        SecurityQuestionsDto securityQuestionsDto = questionsList.get(0).toDto();

        Mockito.when(securityQuestionsRepository.findAll()).thenReturn(questionsList);
        List<SecurityQuestionsDto> expectedResult = securityQuestionsService.retrieveAllQuestions();
        Assertions.assertEquals(expectedResult.get(0),securityQuestionsDto);
    }
}