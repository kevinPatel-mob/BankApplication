package com.mob.casestudy.digitalbanking.repository;

import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CustomerSecurityImageRepository extends JpaRepository<CustomerSecurityImages,String> {


    void deleteCustomerSecurityImagesBySecurityImagesSecurityImageId(String securityImageId);
}
