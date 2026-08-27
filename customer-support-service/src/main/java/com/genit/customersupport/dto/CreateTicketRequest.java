package com.genit.customersupport.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTicketRequest(

		@NotNull(message = "Customer ID is required") Long customerId,

		Long orderId,

		@NotBlank(message = "Subject is required") @Size(max = 200, message = "Subject must not exceed 200 characters") String subject,

		@NotBlank(message = "Description is required") String description

) {
}