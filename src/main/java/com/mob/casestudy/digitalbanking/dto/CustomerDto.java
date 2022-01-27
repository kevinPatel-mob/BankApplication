package com.mob.casestudy.digitalbanking.dto;

import lombok.*;


@NoArgsConstructor
@Getter
public class CustomerDto {

    //TODO: REmove the constructor and use the lombok annotation

    private String userName;
    private String firstName;
    private String lastName;

    private String phoneNumber;

    private String email;

    private String status;

    private String preferredLanguage;


    public CustomerDto(String userName, String firstName, String lastName, String phoneNumber, String email, String status
            , String preferredLanguage) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = status;
        this.preferredLanguage = preferredLanguage;


    }


}


