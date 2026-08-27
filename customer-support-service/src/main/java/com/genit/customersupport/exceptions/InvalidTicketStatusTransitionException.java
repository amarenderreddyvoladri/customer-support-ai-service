package com.genit.customersupport.exceptions;

public class InvalidTicketStatusTransitionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTicketStatusTransitionException(String message) {

		super(message);
	}
}