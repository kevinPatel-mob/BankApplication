package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.compositekey.CustomerImage;
import com.mob.casestudy.digitalbanking.dto.CustomerSecurityImagesDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import com.mob.casestudy.digitalbanking.exceptionresponse.DataNotFoundException;
import com.mob.casestudy.digitalbanking.repository.CustomerRepository;
import com.mob.casestudy.digitalbanking.repository.SecurityImageRepository;
import com.mob.casestudy.digitalbanking.requestbody.CustomerSecurityImageRequestBody;
import com.mob.casestudy.digitalbanking.validator.CustomerDetailValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.mob.casestudy.digitalbanking.errorcodes.CustomisedErrorCodesAndDescription.*;

@Service
public class SecurityImagesService {

    private final CustomerRepository customerRepository;
    private final SecurityImageRepository securityImageRepository;
    private final CustomerDetailValidation customerDetailValidation;
    private final CustomerService customerService;
    private final CustomerImagesService customerImagesService;

    @Autowired
    public SecurityImagesService(CustomerRepository customerRepository, SecurityImageRepository securityImageRepository, CustomerDetailValidation customerDetailValidation, CustomerService customerService, CustomerImagesService customerImagesService) {
        this.customerRepository = customerRepository;
        this.securityImageRepository = securityImageRepository;
        this.customerDetailValidation = customerDetailValidation;
        this.customerService = customerService;
        this.customerImagesService = customerImagesService;
    }

    public CustomerSecurityImagesDto getSecurityImages(String userName) {
        Optional<Customer> customerResultOptional = customerRepository.findByUserName(userName);
        if (customerResultOptional.isEmpty()) {
            throw new DataNotFoundException(USER_NOT_FOUND, USER_NOT_FOUND_DESCRIPTION);
        }
        Customer customer = customerResultOptional.get();
        return customer.getCustomerSecurityImages().toDto();
    }

    public SecurityImages findSecurityImageByIdFromRequestBody(CustomerSecurityImageRequestBody customerSecurityImageRequestBody) {
        Optional<SecurityImages> imageResult = securityImageRepository.findById(customerSecurityImageRequestBody.getSecurityImageId());
        if (imageResult.isEmpty()) {
            throw new DataNotFoundException(CUSTOMER_SECURITY_IMAGE_NOT_IN_TABLE, CUSTOMER_SECURITY_IMAGE_NOT_IN_TABLE_DESCRIPTION);
        }
        return imageResult.get();
    }
    public void validateCustomerSecurityImageAndUpdate(String userName, CustomerSecurityImageRequestBody customerSecurityImageRequestBody) {
        customerDetailValidation.validateCustomerImageCaption(customerSecurityImageRequestBody);
        Customer customer = customerService.findCustomerByName(userName);
        customerImagesService.getCustomerSecurityImageAndDelete(customer);
        SecurityImages securityImageResult = findSecurityImageByIdFromRequestBody(customerSecurityImageRequestBody);
        updateCustomerSecurityImage(securityImageResult, customerSecurityImageRequestBody, customer);
    }
    public void updateCustomerSecurityImage(SecurityImages securityImageResult,CustomerSecurityImageRequestBody customerSecurityImageRequestBody,Customer customer) {
        String securityImageCaption = customerSecurityImageRequestBody.getSecurityImageCaption();
        customerImagesService.saveCustomerAndImageCaption(setCustomerSecurityImages(customer,securityImageResult,securityImageCaption));
    }

    private CustomerSecurityImages setCustomerSecurityImages(Customer customer, SecurityImages securityImageResult, String securityImageCaption) {
        return CustomerSecurityImages.builder().customerImage(new CustomerImage())
                .customer(customer)
                .securityImages(securityImageResult)
                .securityImageCaption(securityImageCaption).build();
    }
}
