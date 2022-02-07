package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.compositekey.CustomerImage;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import com.mob.casestudy.digitalbanking.enums.CustomerStatus;
import com.mob.casestudy.digitalbanking.enums.Language;
import com.mob.casestudy.digitalbanking.exceptionresponse.DataNotFoundException;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityImageRepository;
import com.mob.casestudy.digitalbanking.repository.SecurityImageRepository;
import com.mob.casestudy.digitalbanking.requestbody.CustomerSecurityImageRequestBody;
import com.mob.casestudy.digitalbanking.validator.CustomerDetailValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityImagesServiceTest {

    @InjectMocks
    SecurityImagesService securityImagesService;
    @Mock
    SecurityImageRepository securityImageRepository;
    @Mock
    CustomerDetailValidation customerDetailValidation;
    @Mock
    CustomerService customerService;
    @Mock
    CustomerImagesService customerImagesService;
    @Mock
    CustomerSecurityImageRepository customerSecurityImageRepository;


    @Test
    void findSecurityImageByIdFromRequestBody_If_Image_IsPresent_Then_Return_ImageResult() {
        String id="1a1";
        SecurityImages expected = SecurityImages.builder()
                .securityImageName("DSLR").securityImageUrl("Google.com").build();
        CustomerSecurityImageRequestBody requestBody=CustomerSecurityImageRequestBody.builder()
                .securityImageCaption("kevin").securityImageId(id).build();
        Mockito.when(securityImageRepository.findById(id)).thenReturn(Optional.of(expected));
        SecurityImages actual = securityImagesService.findSecurityImageByIdFromRequestBody(requestBody);
        Assertions.assertEquals(expected,actual);
    }
    @Test
    void findSecurityImageByIdFromRequestBody_If_Image_IsNotPresent_Then_Throw_DataNotFoundException() {
        String id="1a1";
        SecurityImages expected = SecurityImages.builder()
                .securityImageName("DSLR").securityImageUrl("Google.com").build();
        CustomerSecurityImageRequestBody requestBody=CustomerSecurityImageRequestBody.builder()
                .securityImageCaption("kevin").securityImageId(id).build();
        Mockito.when(securityImageRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(DataNotFoundException.class,
                ()->securityImagesService.findSecurityImageByIdFromRequestBody(requestBody));
    }


    @Test
    void validateCustomerSecurityImageAndUpdate() {
        String userName="kep";
        SecurityImages expected = SecurityImages.builder()
                .securityImageName("DSLR").securityImageUrl("Google.com").build();
        CustomerSecurityImageRequestBody requestBody=CustomerSecurityImageRequestBody.builder()
                .securityImageCaption("kevin").securityImageId("11").build();
        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage(Language.EN.toString())
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();
        Mockito.when(customerService.findCustomerByName(userName)).thenReturn(customer);
        Mockito.when(securityImageRepository.findById(requestBody.getSecurityImageId())).thenReturn(Optional.of(expected));
        securityImagesService.validateCustomerSecurityImageAndUpdate(userName,requestBody);
        Mockito.verify(customerDetailValidation).validateCustomerImageCaption(requestBody);
        Mockito.verify(customerImagesService).deleteCustomerSecurityImage(customer);
        Mockito.verify(customerImagesService).saveCustomerAndImageCaption(Mockito.any());
    }


    @Test
    void updateCustomerSecurityImage() {
        Customer customer = Customer.builder().userName("kep")
                .firstName("kevin").lastName("patel")
                .phoneNumber("9664847593").email("kevinpatel1142@gmail.com")
                .status(CustomerStatus.ACTIVE).preferredLanguage(Language.EN.toString())
                .externalId("1").createdBy("self").createdOn(LocalDateTime.now())
                .updatedBy("k-win").updatedOn(LocalDateTime.now()).build();
        SecurityImages expected = SecurityImages.builder()
                .securityImageName("DSLR").securityImageUrl("Google.com").build();
        CustomerSecurityImageRequestBody requestBody=CustomerSecurityImageRequestBody.builder()
                .securityImageCaption("kevin").securityImageId("11").build();
        CustomerSecurityImages customerSecurityImages=CustomerSecurityImages.builder()
                .customerImage(new CustomerImage()).customer(customer)
                .securityImages(expected)
                .securityImageCaption(requestBody.getSecurityImageCaption()).build();

        securityImagesService.updateCustomerSecurityImage(expected,requestBody,customer);
        Mockito.verify(customerImagesService).saveCustomerAndImageCaption(Mockito.any());
    }
}