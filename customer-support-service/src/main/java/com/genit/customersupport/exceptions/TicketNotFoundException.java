package com.genit.customersupport.exceptions;

public class TicketNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TicketNotFoundException(Long ticketId) {
		super("Support ticket not found with id: " + ticketId);
	}
}