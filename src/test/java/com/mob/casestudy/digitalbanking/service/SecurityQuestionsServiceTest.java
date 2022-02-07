package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.compositekey.CustomerQuestions;
import com.mob.casestudy.digitalbanking.dto.CustomerSecurityQuestionsDto;
import com.mob.casestudy.digitalbanking.dto.SecurityQuestionsDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityQuestions;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.enums.CustomerStatus;
import com.mob.casestudy.digitalbanking.enums.Language;
import com.mob.casestudy.digitalbanking.exceptionresponse.DataNotFoundException;
import com.mob.casestudy.digitalbanking.repository.CustomerRepository;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityQuestionRepository;
import com.mob.casestudy.digitalbanking.repository.SecurityQuestionsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class SecurityQuestionsServiceTest {

    @InjectMocks
    SecurityQuestionsService securityQuestionsService;

    @Mock
    SecurityQuestionsRepository securityQuestionsRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerSecurityQuestionRepository customerSecurityQuestionRepository;

    @Test
    void retrieveAllQuestions_If_Questions_Empty_Throw_QuestionEmptyException() {
        List<SecurityQuestions> questionsList = new ArrayList<>();
        Mockito.when(securityQuestionsRepository.findAll()).thenReturn(questionsList);
        Assertions.assertThrows(DataNotFoundException.class
                , () -> securityQuestionsService.retrieveAllQuestions());
    }

    @Test
    void retrieveAllQuestions_If_Questions_Available_Return_SecurityQuestionDto() {
        SecurityQuestions question1 = SecurityQuestions
                .builder().securityQuestionId(String.valueOf(UUID.randomUUID()))
                .securityQuestionText("What is Your Favourite Car").build();
        SecurityQuestions question2 = SecurityQuestions
                .builder().securityQuestionId(String.valueOf(UUID.randomUUID())).
                securityQuestionText("What is Your  ChildHood name").build();
        List<SecurityQuestions> questionsList = new ArrayList<>();
        questionsList.add(question1);
        SecurityQuestionsDto securityQuestionsDto = questionsList.get(0).toDto();
        Mockito.when(securityQuestionsRepository.findAll()).thenReturn(questionsList);
        List<SecurityQuestionsDto> expectedResult = securityQuestionsService.retrieveAllQuestions();
        org.assertj.core.api.Assertions.assertThat(expectedResult.get(0))
                .usingRecursiveComparison().isEqualTo(securityQuestionsDto);
    }

    @Test
    void getSecurityQuestionAndAnswer_If_User_Is_Empty_Throw_Exception() {
        String name = "kep";
        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage(Language.EN.toString())
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();
        Mockito.when(customerRepository.findByUserName(name)).thenReturn(Optional.empty());
        Assertions.assertThrows(DataNotFoundException.class,
                () -> securityQuestionsService.getSecurityQuestionAndAnswer(name));
    }

    @Test
    void getSecurityQuestionAndAnswer_If_User_Available_But_Question_Is_NotAvailable_Throw_Exception() {
        String name = "kep";
        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage(Language.EN.toString())
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();
        Mockito.when(customerRepository.findByUserName(name)).thenReturn(Optional.of(customer));

        Assertions.assertThrows(DataNotFoundException.class,
                () -> securityQuestionsService.getSecurityQuestionAndAnswer(name));
    }

    @Test
    void getSecurityQuestionAndAnswer_If_User_Available_Security_Question_Is_Available_return_CustomerSecurityQuestionDto() {
        String name = "kep";
        SecurityQuestions question1 =
                SecurityQuestions.builder().securityQuestionText("What is Your Favourite Car").build();
        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage(Language.EN.toString())
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).questionsList(new ArrayList<>()).build();
        CustomerSecurityQuestions customerSecurityQuestions1 = CustomerSecurityQuestions.builder().customer(customer).securityQuestions(question1)
                .createdOn("now").customerQuestions(new CustomerQuestions()).securityQuestionAnswer("BMW").build();
        customer.addQuestionsList(customerSecurityQuestions1);
        Mockito.when(customerRepository.findByUserName(name)).thenReturn(Optional.of(customer));
        List<CustomerSecurityQuestionsDto> actual
                = securityQuestionsService.getSecurityQuestionAndAnswer(name);
        List<CustomerSecurityQuestionsDto> expected = new ArrayList<>();
        expected.add(customerSecurityQuestions1.toDto());
        org.assertj.core.api.Assertions.assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    void addCustomerQuestionAnswer() {
        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage(Language.EN.toString())
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).questionsList(new ArrayList<>()).build();
        securityQuestionsService.addCustomerQuestionAnswer(customer);
        Mockito.verify(customerSecurityQuestionRepository, Mockito.times(3)).save(Mockito.any());
    }
}