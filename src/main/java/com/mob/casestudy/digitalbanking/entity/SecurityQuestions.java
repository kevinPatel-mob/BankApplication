package com.mob.casestudy.digitalbanking.entity;

import com.mob.casestudy.digitalbanking.dto.SecurityQuestionsDto;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "securityQuestions")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class SecurityQuestions {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, updatable = false, insertable = false)
    private String securityQuestionId ;


    @Column(length = 50)
    private String securityQuestionText;


    @OneToMany(mappedBy = "securityQuestions")
    private List<CustomerSecurityQuestions> questionsList=new ArrayList<>();

    public SecurityQuestionsDto toDto(){
        return new SecurityQuestionsDto(securityQuestionId,securityQuestionText);
    }


}
