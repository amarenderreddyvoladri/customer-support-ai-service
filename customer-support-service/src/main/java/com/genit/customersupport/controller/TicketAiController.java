package com.genit.customersupport.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genit.customersupport.ai.AiAnalysisService;
import com.genit.customersupport.dto.AiAnalysisResponse;
import com.genit.customersupport.dto.TicketAiAnalysisRequest;
import com.genit.customersupport.entity.TicketAiAnalysis;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketAiController {

	private final AiAnalysisService aiAnalysisService;

	public TicketAiController(AiAnalysisService aiAnalysisService) {

		this.aiAnalysisService = aiAnalysisService;
	}

	@PostMapping("/{ticketId}/ai-analysis")
	public ResponseEntity<AiAnalysisResponse> analyzeTicket(@PathVariable Long ticketId,
			@RequestBody TicketAiAnalysisRequest request) {

		AiAnalysisResponse response = aiAnalysisService.analyzeTicket(ticketId, request.getSubject(),
				request.getDescription());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{ticketId}/ai-analysis")
	public ResponseEntity<TicketAiAnalysis> getAnalysis(@PathVariable Long ticketId) {

		return ResponseEntity.ok(aiAnalysisService.getAnalysis(ticketId));
	}
}