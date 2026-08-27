package com.genit.customersupport.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genit.customersupport.ai.AiProvider;
import com.genit.customersupport.dto.AiAnalysisResponse;
import com.genit.customersupport.dto.TicketAiAnalysisResponse;
import com.genit.customersupport.entity.SupportTicket;
import com.genit.customersupport.entity.TicketAiAnalysis;
import com.genit.customersupport.repo.SupportTicketRepository;
import com.genit.customersupport.repo.TicketAiAnalysisRepository;

@Service
public class TicketAiAnalysisService {

	private final SupportTicketRepository supportTicketRepository;

	private final TicketAiAnalysisRepository ticketAiAnalysisRepository;

	private final AiProvider aiProvider;

	public TicketAiAnalysisService(SupportTicketRepository supportTicketRepository,
			TicketAiAnalysisRepository ticketAiAnalysisRepository, AiProvider aiProvider) {

		this.supportTicketRepository = supportTicketRepository;
		this.ticketAiAnalysisRepository = ticketAiAnalysisRepository;
		this.aiProvider = aiProvider;
	}

	@Transactional
	public TicketAiAnalysisResponse analyzeTicket(Long ticketId) {

		SupportTicket ticket = supportTicketRepository.findById(ticketId)
				.orElseThrow(() -> new RuntimeException("Support ticket not found: " + ticketId));

		TicketAiAnalysis analysis = ticketAiAnalysisRepository.findByTicketId(ticketId)
				.orElseGet(TicketAiAnalysis::new);

		AiAnalysisResponse aiResult = aiProvider.analyzeTicket(ticket.getSubject(), ticket.getDescription());

		analysis.setTicketId(ticket.getId());

		analysis.setIntent(aiResult.getIntent());

		analysis.setSentiment(aiResult.getSentiment());

		analysis.setUrgency(aiResult.getUrgency());

		analysis.setSuggestedCategory(aiResult.getSuggestedCategory());

		analysis.setSuggestedPriority(aiResult.getSuggestedPriority());

		analysis.setAiReasoning(aiResult.getReasoning());

		analysis.setSuggestedResponse(aiResult.getSuggestedResponse());

		TicketAiAnalysis saved = ticketAiAnalysisRepository.save(analysis);

		return mapToResponse(saved);
	}

	@Transactional(readOnly = true)
	public TicketAiAnalysisResponse getAnalysis(Long ticketId) {

		TicketAiAnalysis analysis = ticketAiAnalysisRepository.findByTicketId(ticketId)
				.orElseThrow(() -> new RuntimeException("AI analysis not found for ticket: " + ticketId));

		return mapToResponse(analysis);
	}

	private TicketAiAnalysisResponse mapToResponse(TicketAiAnalysis analysis) {

		TicketAiAnalysisResponse response = new TicketAiAnalysisResponse();

		response.setId(analysis.getId());

		response.setTicketId(analysis.getTicketId());

		response.setIntent(analysis.getIntent());

		response.setSentiment(analysis.getSentiment());

		response.setUrgency(analysis.getUrgency());

		response.setSuggestedCategory(analysis.getSuggestedCategory());

		response.setSuggestedPriority(analysis.getSuggestedPriority());

		response.setAiReasoning(analysis.getAiReasoning());

		response.setSuggestedResponse(analysis.getSuggestedResponse());

		response.setAnalyzedAt(analysis.getAnalyzedAt());

		response.setUpdatedAt(analysis.getUpdatedAt());

		return response;
	}
}