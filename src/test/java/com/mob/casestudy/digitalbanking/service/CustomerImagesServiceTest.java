package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
import com.mob.casestudy.digitalbanking.enums.CustomerStatus;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityImageRepository;
import com.mob.casestudy.digitalbanking.requestbody.CustomerSecurityImageRequestBody;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerImagesServiceTest {

    @InjectMocks
    CustomerImagesService customerImagesService;
    @Mock
    CustomerSecurityImageRepository customerSecurityImageRepository;
    @Mock
    EntityManager entityManager;

    @Test
    void deleteCustomerSecurityImage() {
        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage("EN")
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();
        customerImagesService.deleteCustomerSecurityImage(customer);
        Mockito.verify(customerSecurityImageRepository).delete(customer.getCustomerSecurityImages());
        Mockito.verify(customerSecurityImageRepository).flush();
        Mockito.verify(entityManager).clear();
    }

    @Test
    void saveCustomerAndImageCaption() {
        CustomerSecurityImages securityImages = new CustomerSecurityImages();
        customerImagesService.saveCustomerAndImageCaption(securityImages);
        Mockito.verify(customerSecurityImageRepository).save(securityImages);
    }
}