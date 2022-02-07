package com.mob.casestudy.digitalbanking.requestbody;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerSecurityImageRequestBody {
    private String securityImageId;
    private String securityImageCaption;
}
