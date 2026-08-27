package com.genit.customersupport.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genit.customersupport.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByEmail(String email);

	boolean existsByEmail(String email);
}