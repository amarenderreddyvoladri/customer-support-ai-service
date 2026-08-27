package com.genit.customersupport.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TicketNotFoundException.class)
	public ProblemDetail handleTicketNotFound(TicketNotFoundException exception) {

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());

		problemDetail.setTitle("Support Ticket Not Found");

		return problemDetail;
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ProblemDetail handleCustomerNotFound(CustomerNotFoundException exception) {

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());

		problemDetail.setTitle("Customer Not Found");

		return problemDetail;
	}

	@ExceptionHandler(DuplicateCustomerException.class)
	public ProblemDetail handleDuplicateCustomer(DuplicateCustomerException exception) {

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());

		problemDetail.setTitle("Customer Already Exists");

		return problemDetail;
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ProblemDetail handleOrderNotFound(OrderNotFoundException exception) {

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());

		problemDetail.setTitle("Order Not Found");

		return problemDetail;
	}

	@ExceptionHandler(DuplicateOrderException.class)
	public ProblemDetail handleDuplicateOrder(DuplicateOrderException exception) {

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());

		problemDetail.setTitle("Duplicate Order");

		return problemDetail;
	}

	@ExceptionHandler(InvalidTicketRequestException.class)
	public ProblemDetail handleInvalidTicketRequest(InvalidTicketRequestException exception) {

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());

		problemDetail.setTitle("Invalid Ticket Request");

		return problemDetail;
	}

	@ExceptionHandler(InvalidTicketStatusTransitionException.class)
	public ProblemDetail handleInvalidTicketStatusTransition(InvalidTicketStatusTransitionException exception) {

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());

		problemDetail.setTitle("Invalid Ticket Status Transition");

		return problemDetail;
	}
}