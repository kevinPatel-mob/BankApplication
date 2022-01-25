package com.mob.casestudy.digitalbanking.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CustomerDto {


    private String userName;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String status;

    private String  preferredLanguage;





    public CustomerDto(String userName, String firstName, String lastName, String phoneNumber, String email, String status
            , String preferredLanguage) {
        this.userName=userName;
        this.firstName=firstName;
        this.lastName=lastName;
        this.phoneNumber=phoneNumber;
        this.email=email;
        this.status=status;
        this.preferredLanguage=preferredLanguage;


    }


}


