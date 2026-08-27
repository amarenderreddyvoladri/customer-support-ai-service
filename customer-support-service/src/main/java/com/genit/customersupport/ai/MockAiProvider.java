package com.genit.customersupport.ai;

import com.genit.customersupport.dto.AiAnalysisResponse;
import com.genit.customersupport.enums.AiIntent;
import com.genit.customersupport.enums.AiSentiment;
import com.genit.customersupport.enums.AiUrgency;

//@Component
public class MockAiProvider implements AiProvider {

	@Override
	public AiAnalysisResponse analyzeTicket(String subject, String description) {

		String text = (subject + " " + description).toLowerCase();

		AiAnalysisResponse response = new AiAnalysisResponse();

		if (text.contains("payment") || text.contains("paid") || text.contains("deducted")
				|| text.contains("transaction")) {

			response.setIntent(AiIntent.PAYMENT_ISSUE);
			response.setSuggestedCategory("PAYMENT");
			response.setSuggestedPriority("HIGH");

		} else if (text.contains("refund") || text.contains("money back")) {

			response.setIntent(AiIntent.REFUND_REQUEST);
			response.setSuggestedCategory("REFUND");
			response.setSuggestedPriority("MEDIUM");

		} else if (text.contains("delivery") || text.contains("delivered") || text.contains("shipping")) {

			response.setIntent(AiIntent.DELIVERY_ISSUE);
			response.setSuggestedCategory("DELIVERY");
			response.setSuggestedPriority("MEDIUM");

		} else if (text.contains("cancel")) {

			response.setIntent(AiIntent.ORDER_CANCELLATION);
			response.setSuggestedCategory("ORDER");
			response.setSuggestedPriority("MEDIUM");

		} else if (text.contains("return")) {

			response.setIntent(AiIntent.RETURN_REQUEST);
			response.setSuggestedCategory("RETURN");
			response.setSuggestedPriority("MEDIUM");

		} else {

			response.setIntent(AiIntent.GENERAL_QUERY);
			response.setSuggestedCategory("OTHER");
			response.setSuggestedPriority("LOW");
		}

		if (text.contains("urgent") || text.contains("immediately") || text.contains("angry")
				|| text.contains("terrible")) {

			response.setSentiment(AiSentiment.ANGRY);
			response.setUrgency(AiUrgency.CRITICAL);

		} else if (text.contains("frustrated") || text.contains("disappointed") || text.contains("bad")) {

			response.setSentiment(AiSentiment.FRUSTRATED);
			response.setUrgency(AiUrgency.HIGH);

		} else {

			response.setSentiment(AiSentiment.NEUTRAL);

			if (response.getSuggestedPriority().equals("HIGH")) {
				response.setUrgency(AiUrgency.HIGH);
			} else {
				response.setUrgency(AiUrgency.MEDIUM);
			}
		}

		response.setReasoning("Ticket was analyzed based on customer subject and description.");

		return response;
	}
}