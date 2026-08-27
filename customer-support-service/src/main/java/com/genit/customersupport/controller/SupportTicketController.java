package com.genit.customersupport.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genit.customersupport.dto.CreateTicketRequest;
import com.genit.customersupport.dto.TicketResponse;
import com.genit.customersupport.dto.UpdateTicketPriorityRequest;
import com.genit.customersupport.dto.UpdateTicketStatusRequest;
import com.genit.customersupport.service.SupportTicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tickets")
public class SupportTicketController {

	private final SupportTicketService ticketService;

	public SupportTicketController(SupportTicketService ticketService) {
		this.ticketService = ticketService;
	}

	@PostMapping
	public ResponseEntity<TicketResponse> createTicket(@Valid @RequestBody CreateTicketRequest request) {

		TicketResponse response = ticketService.createTicket(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PatchMapping("/{ticketId}/status")
	public ResponseEntity<TicketResponse> updateStatus(@PathVariable Long ticketId,
			@Valid @RequestBody UpdateTicketStatusRequest request) {

		TicketResponse response = ticketService.updateStatus(ticketId, request.status());

		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{ticketId}/priority")
	public ResponseEntity<TicketResponse> updatePriority(
	        @PathVariable Long ticketId,
	        @Valid @RequestBody UpdateTicketPriorityRequest request) {

	    TicketResponse response =
	            ticketService.updatePriority(
	                    ticketId,
	                    request.priority());

	    return ResponseEntity.ok(response);
	}

	@GetMapping("/{ticketId}")
	public ResponseEntity<TicketResponse> getTicket(@PathVariable Long ticketId) {

		return ResponseEntity.ok(ticketService.getTicket(ticketId));
	}

	@GetMapping
	public ResponseEntity<List<TicketResponse>> getAllTickets() {

		return ResponseEntity.ok(ticketService.getAllTickets());
	}
}