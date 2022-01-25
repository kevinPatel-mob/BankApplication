package com.mob.casestudy.digitalbanking.entity;

import com.mob.casestudy.digitalbanking.compositeKey.CustomerOtpKey;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customerOtp")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class CustomerOtp {


    @EmbeddedId
    private CustomerOtpKey id;

    @OneToOne
    @JoinColumn(name = "customerId")
    @MapsId("customerId")
    private Customer customer;

    @Column(length = 160)
    private String otpMessage;

    @Column(length = 6)
    private String otp;

    @Column(length = 1)
    private Integer otpRetries;

    @Column
    private LocalDateTime expiryOn;

    @Column
    private LocalDateTime createdOn;
}
