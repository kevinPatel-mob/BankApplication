package com.mob.casestudy.digitalbanking.entity;

import com.mob.casestudy.digitalbanking.compositekey.CustomerImage;
import com.mob.casestudy.digitalbanking.dto.CustomerSecurityImagesDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "customerSecurityImages")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Getter
public class CustomerSecurityImages {

    @EmbeddedId
    private CustomerImage customerImage = new CustomerImage();
    @Column(length = 50)
    private String securityImageCaption;
    @Column(length = 50)
    private String createdOn;
    @ManyToOne
    @JoinColumn(name = "securityImageId")
    @MapsId("securityImageId")
    private SecurityImages securityImages;

    @OneToOne
    @MapsId("customerId")
    private Customer customer;

    public CustomerSecurityImagesDto toDto(){
        return new CustomerSecurityImagesDto(securityImages.getSecurityImageId()
                ,securityImages.getSecurityImageName()
                ,securityImageCaption,securityImages.getSecurityImageUrl());
    }


}
