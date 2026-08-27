package com.genit.customersupport.exceptions;

public class DuplicateOrderException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateOrderException(String orderNumber) {

		super("Order already exists with order number: " + orderNumber);
	}
}
