package com.genit.customersupport.dto;

import java.time.LocalDateTime;

import com.genit.customersupport.enums.CustomerStatus;

public record CustomerResponse(

		Long id,

		String firstName,

		String lastName,

		String email,

		String phone,

		CustomerStatus status,

		LocalDateTime createdAt,

		LocalDateTime updatedAt

) {
}