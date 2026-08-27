package com.genit.customersupport.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genit.customersupport.dto.TicketAiAnalysisResponse;
import com.genit.customersupport.service.TicketAiAnalysisService;

@RestController
@RequestMapping("/api/v1/ticket-ai-analysis")
public class TicketAiAnalysisController {

	private final TicketAiAnalysisService ticketAiAnalysisService;

	public TicketAiAnalysisController(TicketAiAnalysisService ticketAiAnalysisService) {

		this.ticketAiAnalysisService = ticketAiAnalysisService;
	}

	// =====================================================
	// FUNCTIONALITY 5
	// Generate / refresh AI analysis
	// =====================================================

	@PostMapping("/{ticketId}/ai-analysis")
	public ResponseEntity<TicketAiAnalysisResponse> analyzeTicket(@PathVariable Long ticketId) {

		TicketAiAnalysisResponse response = ticketAiAnalysisService.analyzeTicket(ticketId);

		return ResponseEntity.ok(response);
	}

	// =====================================================
	// FUNCTIONALITY 6
	// Get existing AI analysis
	// =====================================================

	@GetMapping("/{ticketId}/ai-analysis")
	public ResponseEntity<TicketAiAnalysisResponse> getAnalysis(@PathVariable Long ticketId) {

		TicketAiAnalysisResponse response = ticketAiAnalysisService.getAnalysis(ticketId);

		return ResponseEntity.ok(response);
	}
}
