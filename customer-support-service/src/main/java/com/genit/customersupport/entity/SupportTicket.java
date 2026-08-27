package com.genit.customersupport.entity;

import java.time.LocalDateTime;

import com.genit.customersupport.enums.TicketCategory;
import com.genit.customersupport.enums.TicketPriority;
import com.genit.customersupport.enums.TicketStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "support_tickets")
public class SupportTicket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long customerId;

	private Long orderId;

	@Column(nullable = false, length = 200)
	private String subject;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private TicketStatus status;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private TicketPriority priority;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private TicketCategory category;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Column(name = "ai_processed", nullable = false)
	private boolean aiProcessed = false;

	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();

		createdAt = now;
		updatedAt = now;

		if (status == null) {
			status = TicketStatus.OPEN;
		}

		if (priority == null) {
			priority = TicketPriority.MEDIUM;
		}

		if (category == null) {
			category = TicketCategory.OTHER;
		}
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public TicketPriority getPriority() {
		return priority;
	}

	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}

	public TicketCategory getCategory() {
		return category;
	}

	public void setCategory(TicketCategory category) {
		this.category = category;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public boolean isAiProcessed() {
		return aiProcessed;
	}

	public void setAiProcessed(boolean aiProcessed) {
		this.aiProcessed = aiProcessed;
	}
}