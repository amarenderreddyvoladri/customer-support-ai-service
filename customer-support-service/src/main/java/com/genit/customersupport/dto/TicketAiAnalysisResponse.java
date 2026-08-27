package com.genit.customersupport.dto;

import java.time.LocalDateTime;

import com.genit.customersupport.enums.AiIntent;
import com.genit.customersupport.enums.AiSentiment;
import com.genit.customersupport.enums.AiUrgency;

public class TicketAiAnalysisResponse {

	private Long id;
	private Long ticketId;
	private AiIntent intent;
	private AiSentiment sentiment;
	private AiUrgency urgency;
	private String suggestedCategory;
	private String suggestedPriority;
	private String aiReasoning;
	private String suggestedResponse;
	private LocalDateTime analyzedAt;
	private LocalDateTime updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public AiIntent getIntent() {
		return intent;
	}

	public void setIntent(AiIntent intent) {
		this.intent = intent;
	}

	public AiSentiment getSentiment() {
		return sentiment;
	}

	public void setSentiment(AiSentiment sentiment) {
		this.sentiment = sentiment;
	}

	public AiUrgency getUrgency() {
		return urgency;
	}

	public void setUrgency(AiUrgency urgency) {
		this.urgency = urgency;
	}

	public String getSuggestedCategory() {
		return suggestedCategory;
	}

	public void setSuggestedCategory(String suggestedCategory) {
		this.suggestedCategory = suggestedCategory;
	}

	public String getSuggestedPriority() {
		return suggestedPriority;
	}

	public void setSuggestedPriority(String suggestedPriority) {
		this.suggestedPriority = suggestedPriority;
	}

	public String getAiReasoning() {
		return aiReasoning;
	}

	public void setAiReasoning(String aiReasoning) {
		this.aiReasoning = aiReasoning;
	}

	public String getSuggestedResponse() {
		return suggestedResponse;
	}

	public void setSuggestedResponse(String suggestedResponse) {
		this.suggestedResponse = suggestedResponse;
	}

	public LocalDateTime getAnalyzedAt() {
		return analyzedAt;
	}

	public void setAnalyzedAt(LocalDateTime analyzedAt) {
		this.analyzedAt = analyzedAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}