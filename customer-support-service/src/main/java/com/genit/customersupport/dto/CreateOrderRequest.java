package com.genit.customersupport.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateOrderRequest(

		@NotNull(message = "Customer ID is required") Long customerId,

		@NotBlank(message = "Order number is required") @Size(max = 50, message = "Order number must not exceed 50 characters") String orderNumber,

		@NotNull(message = "Total amount is required") @DecimalMin(value = "0.01", message = "Total amount must be greater than zero") BigDecimal totalAmount,

		@NotBlank(message = "Currency is required") @Size(min = 3, max = 3, message = "Currency must be a 3-letter code") String currency,

		OrderStatus status

) {
}