package com.github.salesnotification.consumer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.salesnotification.consumer.domain.SalesInfo;

@Repository
public interface SalesRepository extends JpaRepository<SalesInfo, Long> {
}
