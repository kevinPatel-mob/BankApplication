package com.mob.casestudy.digitalbanking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerSecurityQuestionsResponse {
    private List<CustomerSecurityQuestionsDto> securityQuestions;
}

