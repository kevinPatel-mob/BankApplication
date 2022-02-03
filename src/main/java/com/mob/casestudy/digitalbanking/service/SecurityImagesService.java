package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.compositekey.CustomerImage;
import com.mob.casestudy.digitalbanking.dto.CustomerSecurityImagesDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import com.mob.casestudy.digitalbanking.exceptionresponse.DataNotFoundException;
import com.mob.casestudy.digitalbanking.repository.CustomerRepository;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityImageRepository;
import com.mob.casestudy.digitalbanking.repository.SecurityImageRepository;
import com.mob.casestudy.digitalbanking.requestbody.CustomerSecurityImageRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.mob.casestudy.digitalbanking.errorcodes.CustomisedErrorCodesAndDescription.*;

@Service
public class SecurityImagesService {

    private CustomerRepository customerRepository;
    private final CustomerSecurityImageRepository customerSecurityImageRepository;
    private final SecurityImageRepository securityImageRepository;
    private final EntityManager entityManager;
    @Autowired
    public SecurityImagesService(CustomerRepository customerRepository, CustomerSecurityImageRepository customerSecurityImageRepository, SecurityImageRepository securityImageRepository, EntityManager entityManager) {
        this.customerRepository = customerRepository;
        this.customerSecurityImageRepository = customerSecurityImageRepository;
        this.securityImageRepository = securityImageRepository;
        this.entityManager = entityManager;
    }

    public CustomerSecurityImagesDto getSecurityImages(String userName){
        Optional<Customer> customerResultOptional = customerRepository.findByUserName(userName);
        if (!customerResultOptional.isPresent()) {
            throw new DataNotFoundException(USER_NOT_FOUND,USER_NOT_FOUND_DESCRIPTION);
        }
        Customer customer = customerResultOptional.get();
        return customer.getCustomerSecurityImages().toDto();
    }


    @Transactional
    public void updateCustomerSecurityImage(String userName,CustomerSecurityImageRequestBody customerSecurityImageRequestBody) {
        Optional<Customer> customerResultOptional = customerRepository.findByUserName(userName);
        if (!customerResultOptional.isPresent()) {
            throw new DataNotFoundException(USER_NOT_FOUND,USER_NOT_FOUND_DESCRIPTION);
        }
        Customer customer = customerResultOptional.get();
        String securityImageId = customer.getCustomerSecurityImages().getSecurityImages().getSecurityImageId();
        String securityImageCaption = customerSecurityImageRequestBody.getSecurityImageCaption();
        Optional<SecurityImages> imageResult = securityImageRepository.findById(securityImageId);
        if (imageResult.isEmpty()){
            throw new DataNotFoundException(CUSTOMER_SECURITY_IMAGE_NOT_IN_TABLE,CUSTOMER_SECURITY_IMAGE_NOT_IN_TABLE_DESCRIPTION);
        }
        customerSecurityImageRepository.deleteCustomerSecurityImagesBySecurityImages_SecurityImageId(securityImageId);
        entityManager.flush();
        entityManager.clear();

        CustomerSecurityImages customerSecurityImages = CustomerSecurityImages.builder().customerImage(new CustomerImage()).customer(customer)
                .securityImages(imageResult.get()).securityImageCaption(securityImageCaption).build();
        customerSecurityImageRepository.save(customerSecurityImages);
    }
}
