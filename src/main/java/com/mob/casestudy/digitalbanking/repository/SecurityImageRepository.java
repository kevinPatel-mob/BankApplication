package com.mob.casestudy.digitalbanking.repository;

import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SecurityImageRepository extends JpaRepository<SecurityImages,String> {
}
