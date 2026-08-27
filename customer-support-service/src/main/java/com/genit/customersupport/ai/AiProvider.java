package com.genit.customersupport.ai;

import com.genit.customersupport.dto.AiAnalysisResponse;

public interface AiProvider {

	AiAnalysisResponse analyzeTicket(String subject, String description);
}