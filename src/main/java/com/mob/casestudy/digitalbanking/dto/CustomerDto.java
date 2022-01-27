package com.mob.casestudy.digitalbanking.dto;

import lombok.*;


@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CustomerDto {

    //TODO: REmove the constructor and use the lombok annotation

    private String userName;
    private String firstName;
    private String lastName;

    private String phoneNumber;

    private String email;

    private String status;

    private String preferredLanguage;





}


