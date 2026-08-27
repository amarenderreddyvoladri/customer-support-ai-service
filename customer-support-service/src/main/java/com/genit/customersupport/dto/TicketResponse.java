package com.genit.customersupport.dto;

import java.time.LocalDateTime;

import com.genit.customersupport.enums.TicketCategory;
import com.genit.customersupport.enums.TicketPriority;
import com.genit.customersupport.enums.TicketStatus;

public record TicketResponse(

		Long id,

		Long customerId,

		Long orderId,

		String subject,

		String description,

		TicketStatus status,

		TicketPriority priority,

		TicketCategory category,

		LocalDateTime createdAt,

		LocalDateTime updatedAt

) {
}
