package com.mob.casestudy.digitalbanking.dto;

import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SecurityImagesDto {


    private String securityImageId ;

    private String securityImageName;

    private String securityImageUrl;

    private List<CustomerSecurityImages> customerSecurityImagesList=new ArrayList<>();

}
