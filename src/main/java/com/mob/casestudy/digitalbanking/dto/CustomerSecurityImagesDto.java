package com.mob.casestudy.digitalbanking.dto;

import com.mob.casestudy.digitalbanking.compositekey.CustomerImage;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerSecurityImagesDto {
    private CustomerImage customerImage;
    private String securityImageCaption;
    private String createdOn;
    private SecurityImages securityImages;
    private Customer customer;
}
