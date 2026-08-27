package com.genit.customersupport.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genit.customersupport.dto.CreateOrderRequest;
import com.genit.customersupport.dto.OrderResponse;
import com.genit.customersupport.service.CustomerOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
public class CustomerOrderController {

	private final CustomerOrderService orderService;

	public CustomerOrderController(CustomerOrderService orderService) {

		this.orderService = orderService;
	}

	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {

		OrderResponse response = orderService.createOrder(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {

		return ResponseEntity.ok(orderService.getOrder(orderId));
	}

	@GetMapping
	public ResponseEntity<List<OrderResponse>> getAllOrders() {

		return ResponseEntity.ok(orderService.getAllOrders());
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<OrderResponse>> getOrdersByCustomer(@PathVariable Long customerId) {

		return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
	}
}