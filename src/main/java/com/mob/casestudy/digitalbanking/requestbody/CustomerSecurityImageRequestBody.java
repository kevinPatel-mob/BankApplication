package com.mob.casestudy.digitalbanking.requestbody;


import lombok.Getter;

@Getter
public class CustomerSecurityImageRequestBody {
    private String securityImageId;
    private String securityImageCaption;
}
