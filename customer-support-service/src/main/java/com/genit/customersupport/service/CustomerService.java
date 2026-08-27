package com.genit.customersupport.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genit.customersupport.dto.CreateCustomerRequest;
import com.genit.customersupport.dto.CustomerResponse;
import com.genit.customersupport.entity.Customer;
import com.genit.customersupport.exceptions.CustomerNotFoundException;
import com.genit.customersupport.exceptions.DuplicateCustomerException;
import com.genit.customersupport.repo.CustomerRepository;

@Service
@Transactional
public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {

		this.customerRepository = customerRepository;
	}

	public CustomerResponse createCustomer(CreateCustomerRequest request) {

		if (customerRepository.existsByEmail(request.email())) {
			throw new DuplicateCustomerException("Customer already exists with email: " + request.email());
		}

		Customer customer = new Customer();

		customer.setFirstName(request.firstName());
		customer.setLastName(request.lastName());
		customer.setEmail(request.email());
		customer.setPhone(request.phone());

		Customer savedCustomer = customerRepository.save(customer);

		return mapToResponse(savedCustomer);
	}

	@Transactional(readOnly = true)
	public CustomerResponse getCustomer(Long customerId) {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		return mapToResponse(customer);
	}

	@Transactional(readOnly = true)
	public List<CustomerResponse> getAllCustomers() {

		return customerRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	private CustomerResponse mapToResponse(Customer customer) {

		return new CustomerResponse(customer.getId(), customer.getFirstName(), customer.getLastName(),
				customer.getEmail(), customer.getPhone(), customer.getStatus(), customer.getCreatedAt(),
				customer.getUpdatedAt());
	}
}