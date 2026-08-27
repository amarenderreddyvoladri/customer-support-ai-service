package com.genit.customersupport.dto;

import com.genit.customersupport.enums.AiIntent;
import com.genit.customersupport.enums.AiSentiment;
import com.genit.customersupport.enums.AiUrgency;

import lombok.Data;

@Data
public class AiAnalysisResponse {

	private AiIntent intent;

	private AiSentiment sentiment;

	private AiUrgency urgency;

	private String suggestedCategory;

	private String suggestedPriority;

	private String reasoning;

	private String suggestedResponse;

}