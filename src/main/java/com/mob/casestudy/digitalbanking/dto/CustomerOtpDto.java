package com.mob.casestudy.digitalbanking.dto;

import com.mob.casestudy.digitalbanking.compositekey.CustomerOtpKey;
import com.mob.casestudy.digitalbanking.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerOtpDto {
    private CustomerOtpKey id;
    private Customer customer;
    private String otpMessage;
    private String otp;
    private Integer otpRetries;
    private LocalDateTime expiryOn;
    private LocalDateTime createdOn;
}
