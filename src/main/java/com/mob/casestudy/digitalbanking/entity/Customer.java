package com.mob.casestudy.digitalbanking.entity;

import com.mob.casestudy.digitalbanking.dto.CustomerDto;
import com.mob.casestudy.digitalbanking.enums.CustomerStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class Customer {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, updatable = false, insertable = false)
    private String customerId;

    @Column
    @Length(max = 30,min = 3)
    private String userName;


    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(length = 10)
    private String phoneNumber;

    @Column(length = 50)
    private String email;

    @Column(name = "status",length = 20)
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @Column(length = 2)
   // @Enumerated(EnumType.STRING)
    private String preferredLanguage;

    @Column(length = 50)
    @Length(max = 50)
    private String externalId= userName+"_ext";

    @Column(length = 50)
    private String createdBy;

    @Column
    private LocalDateTime createdOn;

    @Column(length = 50)
    private String updatedBy;

    @Column
    private LocalDateTime updatedOn;

    @OneToOne(mappedBy = "customer")
    private CustomerOtp customerOtp;


    @OneToMany(mappedBy = "customer")
    private List<CustomerSecurityQuestions> questionsList=new ArrayList<>();


    @OneToOne(mappedBy = "customer")
    private CustomerSecurityImages customerSecurityImages;

    public CustomerDto toDto(){
        return new CustomerDto(userName,firstName,lastName,phoneNumber,email,status.name(),preferredLanguage);
    }

}
