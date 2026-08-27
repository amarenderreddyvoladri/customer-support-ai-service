package com.genit.customersupport.entity;

import java.time.LocalDateTime;

import com.genit.customersupport.enums.AiIntent;
import com.genit.customersupport.enums.AiSentiment;
import com.genit.customersupport.enums.AiUrgency;

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
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "ticket_ai_analysis", uniqueConstraints = {
		@UniqueConstraint(name = "uk_ticket_ai_analysis_ticket", columnNames = "ticket_id") })
public class TicketAiAnalysis {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ticket_id", nullable = false)
	private Long ticketId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private AiIntent intent;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private AiSentiment sentiment;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private AiUrgency urgency;

	@Column(name = "suggested_category", nullable = false, length = 30)
	private String suggestedCategory;

	@Column(name = "suggested_priority", nullable = false, length = 30)
	private String suggestedPriority;

	@Column(name = "ai_reasoning", columnDefinition = "TEXT")
	private String aiReasoning;

	@Column(name = "suggested_response", columnDefinition = "TEXT")
	private String suggestedResponse;

	@Column(name = "analyzed_at", nullable = false)
	private LocalDateTime analyzedAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {

		LocalDateTime now = LocalDateTime.now();

		if (analyzedAt == null) {
			analyzedAt = now;
		}

		if (updatedAt == null) {
			updatedAt = now;
		}
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}