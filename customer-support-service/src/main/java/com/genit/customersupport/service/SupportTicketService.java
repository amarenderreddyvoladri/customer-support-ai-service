package com.genit.customersupport.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genit.customersupport.dto.CreateTicketRequest;
import com.genit.customersupport.dto.TicketResponse;
import com.genit.customersupport.entity.SupportTicket;
import com.genit.customersupport.enums.TicketPriority;
import com.genit.customersupport.enums.TicketStatus;
import com.genit.customersupport.exceptions.CustomerNotFoundException;
import com.genit.customersupport.exceptions.InvalidTicketRequestException;
import com.genit.customersupport.exceptions.InvalidTicketStatusTransitionException;
import com.genit.customersupport.exceptions.TicketNotFoundException;
import com.genit.customersupport.repo.CustomerOrderRepository;
import com.genit.customersupport.repo.CustomerRepository;
import com.genit.customersupport.repo.SupportTicketRepository;

@Service
@Transactional
public class SupportTicketService {

	private final SupportTicketRepository ticketRepository;

	private final CustomerRepository customerRepository;

	private final CustomerOrderRepository orderRepository;

	public SupportTicketService(CustomerRepository customerRepository, CustomerOrderRepository orderRepository,
			SupportTicketRepository ticketRepository) {

		this.customerRepository = customerRepository;
		this.orderRepository = orderRepository;
		this.ticketRepository = ticketRepository;
	}

	public TicketResponse createTicket(CreateTicketRequest request) {

		// 1. Validate customer
		customerRepository.findById(request.customerId())
				.orElseThrow(() -> new CustomerNotFoundException(request.customerId()));

		// 2. Validate order if provided
		if (request.orderId() != null) {

			orderRepository.findByIdAndCustomerId(request.orderId(), request.customerId())
					.orElseThrow(() -> new InvalidTicketRequestException(
							"Order " + request.orderId() + " does not belong to customer " + request.customerId()));
		}

		// 3. Create ticket
		SupportTicket ticket = new SupportTicket();

		ticket.setCustomerId(request.customerId());
		ticket.setOrderId(request.orderId());
		ticket.setSubject(request.subject());
		ticket.setDescription(request.description());

		SupportTicket savedTicket = ticketRepository.save(ticket);

		return mapToResponse(savedTicket);
	}

	@Transactional(readOnly = true)
	public TicketResponse getTicket(Long ticketId) {

		SupportTicket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new TicketNotFoundException(ticketId));

		return mapToResponse(ticket);
	}

	@Transactional(readOnly = true)
	public List<TicketResponse> getAllTickets() {

		return ticketRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	@Transactional
	public TicketResponse updateStatus(Long ticketId, TicketStatus newStatus) {

		SupportTicket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new TicketNotFoundException(ticketId));

		TicketStatus currentStatus = ticket.getStatus();

		validateStatusTransition(currentStatus, newStatus);

		ticket.setStatus(newStatus);

		SupportTicket updatedTicket = ticketRepository.save(ticket);

		return mapToResponse(updatedTicket);
	}

	@Transactional
	public TicketResponse updatePriority(Long ticketId, TicketPriority newPriority) {

		SupportTicket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new TicketNotFoundException(ticketId));

		ticket.setPriority(newPriority);

		SupportTicket updatedTicket = ticketRepository.save(ticket);

		return mapToResponse(updatedTicket);
	}

	private void validateStatusTransition(TicketStatus currentStatus, TicketStatus newStatus) {

		if (currentStatus == newStatus) {
			return;
		}

		boolean validTransition = switch (currentStatus) {

		case OPEN -> newStatus == TicketStatus.IN_PROGRESS;

		case IN_PROGRESS -> newStatus == TicketStatus.OPEN || newStatus == TicketStatus.RESOLVED;

		case RESOLVED -> newStatus == TicketStatus.CLOSED || newStatus == TicketStatus.IN_PROGRESS;

		case CLOSED -> false;
		};

		if (!validTransition) {

			throw new InvalidTicketStatusTransitionException(
					"Invalid ticket status transition from " + currentStatus + " to " + newStatus);
		}
	}

	private TicketResponse mapToResponse(SupportTicket ticket) {

		return new TicketResponse(ticket.getId(), ticket.getCustomerId(), ticket.getOrderId(), ticket.getSubject(),
				ticket.getDescription(), ticket.getStatus(), ticket.getPriority(), ticket.getCategory(),
				ticket.getCreatedAt(), ticket.getUpdatedAt());
	}
}