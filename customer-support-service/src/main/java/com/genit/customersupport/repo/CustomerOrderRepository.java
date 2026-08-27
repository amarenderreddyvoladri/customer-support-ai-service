package com.genit.customersupport.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genit.customersupport.entity.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

	boolean existsByOrderNumber(String orderNumber);

	Optional<CustomerOrder> findByOrderNumber(String orderNumber);

	List<CustomerOrder> findByCustomerId(Long customerId);

	Optional<CustomerOrder> findByIdAndCustomerId(Long orderId, Long customerId);
}