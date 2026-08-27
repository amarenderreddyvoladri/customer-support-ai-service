package com.genit.customersupport.dto;

import com.genit.customersupport.enums.TicketStatus;

import jakarta.validation.constraints.NotNull;

public record UpdateTicketStatusRequest(

		@NotNull(message = "Status is required") TicketStatus status

) {
}
