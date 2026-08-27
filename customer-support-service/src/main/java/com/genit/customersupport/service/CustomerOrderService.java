package com.genit.customersupport.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genit.customersupport.dto.CreateOrderRequest;
import com.genit.customersupport.dto.OrderResponse;
import com.genit.customersupport.entity.CustomerOrder;
import com.genit.customersupport.exceptions.DuplicateOrderException;
import com.genit.customersupport.exceptions.OrderNotFoundException;
import com.genit.customersupport.repo.CustomerOrderRepository;

@Service
@Transactional
public class CustomerOrderService {

	private final CustomerOrderRepository orderRepository;

	public CustomerOrderService(CustomerOrderRepository orderRepository) {

		this.orderRepository = orderRepository;
	}

	public OrderResponse createOrder(CreateOrderRequest request) {

		if (orderRepository.existsByOrderNumber(request.orderNumber())) {

			throw new DuplicateOrderException(request.orderNumber());
		}

		CustomerOrder order = new CustomerOrder();

		order.setCustomerId(request.customerId());
		order.setOrderNumber(request.orderNumber());
		order.setTotalAmount(request.totalAmount());
		order.setCurrency(request.currency().toUpperCase());

		if (request.status() != null) {
			order.setStatus(request.status());
		}

		CustomerOrder savedOrder = orderRepository.save(order);

		return mapToResponse(savedOrder);
	}

	@Transactional(readOnly = true)
	public OrderResponse getOrder(Long orderId) {

		CustomerOrder order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));

		return mapToResponse(order);
	}

	@Transactional(readOnly = true)
	public List<OrderResponse> getAllOrders() {

		return orderRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	@Transactional(readOnly = true)
	public List<OrderResponse> getOrdersByCustomer(Long customerId) {

		return orderRepository.findByCustomerId(customerId).stream().map(this::mapToResponse).toList();
	}

	private OrderResponse mapToResponse(CustomerOrder order) {

		return new OrderResponse(order.getId(), order.getCustomerId(), order.getOrderNumber(), order.getTotalAmount(),
				order.getCurrency(), order.getStatus(), order.getCreatedAt(), order.getUpdatedAt());
	}
}