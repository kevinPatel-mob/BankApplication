package com.mob.casestudy.digitalbanking.compositekey;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CustomerQuestions implements Serializable {


    private String customerId;


    private String securityQuestionId;


}
