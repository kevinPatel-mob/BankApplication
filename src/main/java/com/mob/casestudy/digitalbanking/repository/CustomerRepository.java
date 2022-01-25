package com.mob.casestudy.digitalbanking.repository;

import com.mob.casestudy.digitalbanking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer,String> {

    Optional<Customer> findByUserName(String name);





}
