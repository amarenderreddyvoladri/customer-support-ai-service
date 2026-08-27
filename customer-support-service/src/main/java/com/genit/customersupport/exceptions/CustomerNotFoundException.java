package com.genit.customersupport.exceptions;

public class CustomerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(Long customerId) {

		super("Customer not found with id: " + customerId);
	}
}