package com.mob.casestudy.digitalbanking.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerSecurityImagesDto {
    private String securityImageId;
    private String securityImageName;
    private String securityImageCaption;
    private String securityImageUrl;


}
