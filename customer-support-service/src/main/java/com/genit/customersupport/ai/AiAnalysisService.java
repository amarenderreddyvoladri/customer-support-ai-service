package com.genit.customersupport.ai;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genit.customersupport.dto.AiAnalysisResponse;
import com.genit.customersupport.entity.TicketAiAnalysis;
import com.genit.customersupport.repo.TicketAiAnalysisRepository;

@Service
public class AiAnalysisService {

	private final AiProvider aiProvider;

	private final TicketAiAnalysisRepository analysisRepository;

	public AiAnalysisService(AiProvider aiProvider, TicketAiAnalysisRepository analysisRepository) {

		this.aiProvider = aiProvider;
		this.analysisRepository = analysisRepository;
	}

	@Transactional
	public AiAnalysisResponse analyzeTicket(Long ticketId, String subject, String description) {

		AiAnalysisResponse response = aiProvider.analyzeTicket(subject, description);

		TicketAiAnalysis analysis = analysisRepository.findByTicketId(ticketId).orElseGet(TicketAiAnalysis::new);

		analysis.setTicketId(ticketId);
		analysis.setIntent(response.getIntent());
		analysis.setSentiment(response.getSentiment());
		analysis.setUrgency(response.getUrgency());
		analysis.setSuggestedCategory(response.getSuggestedCategory());
		analysis.setSuggestedPriority(response.getSuggestedPriority());
		analysis.setAiReasoning(response.getReasoning());

		analysisRepository.save(analysis);

		return response;
	}

	public TicketAiAnalysis getAnalysis(Long ticketId) {

		return analysisRepository.findByTicketId(ticketId)
				.orElseThrow(() -> new RuntimeException("AI analysis not found for ticket: " + ticketId));
	}
}