package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service

public class CustomerImagesService {

    private final CustomerSecurityImageRepository customerSecurityImageRepository;
    private final EntityManager entityManager;

    @Autowired
    public CustomerImagesService(CustomerSecurityImageRepository customerSecurityImageRepository, EntityManager entityManager) {
        this.customerSecurityImageRepository = customerSecurityImageRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public void deleteCustomerSecurityImage(Customer customer) {
        customerSecurityImageRepository.delete(customer.getCustomerSecurityImages());
        customerSecurityImageRepository.flush();
        entityManager.clear();
    }

    public void saveCustomerAndImageCaption(CustomerSecurityImages customerSecurityImages) {
        customerSecurityImageRepository.save(customerSecurityImages);
    }
}
