package com.mob.casestudy.digitalbanking.entity;

import com.mob.casestudy.digitalbanking.compositekey.CustomerQuestions;
import com.mob.casestudy.digitalbanking.dto.CustomerSecurityQuestionsDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "customerSecurityQuestions")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Getter
public class CustomerSecurityQuestions {
    @EmbeddedId
    private CustomerQuestions customerQuestions=new CustomerQuestions();
    @Column(length = 50)
    private String securityQuestionAnswer;
    @Column(length = 50)
    private String createdOn;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("customerId")
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "securityQuestionId")
    @MapsId("securityQuestionId")
    private SecurityQuestions securityQuestions;

    public CustomerSecurityQuestionsDto toDto(){
        return new CustomerSecurityQuestionsDto(
                securityQuestions.getSecurityQuestionId(),
                securityQuestions.getSecurityQuestionText(),
                securityQuestionAnswer);
    }
}
