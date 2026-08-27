package com.genit.customersupport.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(

		Long id,

		Long customerId,

		String orderNumber,

		BigDecimal totalAmount,

		String currency,

		OrderStatus status,

		LocalDateTime createdAt,

		LocalDateTime updatedAt

) {
}