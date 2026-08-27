package com.genit.customersupport.dto;

import com.genit.customersupport.enums.TicketPriority;

import jakarta.validation.constraints.NotNull;

public record UpdateTicketPriorityRequest(

		@NotNull(message = "Priority is required") TicketPriority priority

) {
}