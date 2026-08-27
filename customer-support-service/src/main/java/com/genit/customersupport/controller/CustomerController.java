package com.genit.customersupport.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genit.customersupport.dto.CreateCustomerRequest;
import com.genit.customersupport.dto.CustomerResponse;
import com.genit.customersupport.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {

		this.customerService = customerService;
	}

	@PostMapping
	public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {

		CustomerResponse response = customerService.createCustomer(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long customerId) {

		return ResponseEntity.ok(customerService.getCustomer(customerId));
	}

	@GetMapping
	public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

		return ResponseEntity.ok(customerService.getAllCustomers());
	}
}