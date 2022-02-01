package com.mob.casestudy.digitalbanking.compositekey;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode

public class CustomerOtpKey implements Serializable {
    private String customerId ;
    @Length(max = 36)
    private String otpId = String.valueOf(UUID.randomUUID());
}
