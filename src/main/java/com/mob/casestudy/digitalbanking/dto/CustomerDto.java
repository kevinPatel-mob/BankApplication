package com.mob.casestudy.digitalbanking.dto;

import lombok.*;
import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CustomerDto {

    private String userName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String status;
    private String preferredLanguage;

    public Map<String,String> toMap() {
        Map <String,String> map=new HashMap<>();
        if (isEmpty(userName)) map.put("userName",userName);
        if (isEmpty(firstName)) map.put("firstName",firstName);
        if (isEmpty(lastName)) map.put("lastName",lastName);
        if (isEmpty(phoneNumber)) map.put("phoneNumber",phoneNumber);
        if (isEmpty(email)) map.put("email",email);
        if (isEmpty(status)) map.put("status",status);
        if (isEmpty(preferredLanguage)) map.put("preferredLanguage",preferredLanguage);
        return map;
    }
    private boolean isEmpty(String name){
        return name!=null && !name.isEmpty();
    }
}


