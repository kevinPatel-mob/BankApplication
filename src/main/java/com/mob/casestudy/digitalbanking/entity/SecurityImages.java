package com.mob.casestudy.digitalbanking.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "securityImages")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class SecurityImages {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, updatable = false, insertable = false)
    private String securityImageId ;
    @Column(length = 50)
    private String securityImageName;
    @Column(length = 255)
    private String securityImageUrl;
    @OneToMany(mappedBy = "securityImages",fetch = FetchType.LAZY)
    private List<CustomerSecurityImages> customerSecurityImagesList=new ArrayList<>();
}
