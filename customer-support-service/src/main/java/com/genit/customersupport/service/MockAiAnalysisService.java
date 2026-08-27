package com.genit.customersupport.service;

import org.springframework.stereotype.Service;

import com.genit.customersupport.entity.SupportTicket;
import com.genit.customersupport.enums.AiIntent;
import com.genit.customersupport.enums.AiSentiment;
import com.genit.customersupport.enums.AiUrgency;

@Service
public class MockAiAnalysisService {

	public AiAnalysisResult analyze(SupportTicket ticket) {

		String text = (ticket.getSubject() + " " + ticket.getDescription()).toLowerCase();

		AiIntent intent;
		String suggestedCategory;
		String suggestedPriority;
		String suggestedResponse;

		if (text.contains("payment") || text.contains("paid") || text.contains("deducted")
				|| text.contains("transaction") || text.contains("charged")) {

			intent = AiIntent.PAYMENT_ISSUE;
			suggestedCategory = "PAYMENT";
			suggestedPriority = "HIGH";
			suggestedResponse = "We understand your concern regarding the payment. "
					+ "Our team will verify the transaction details and assist you further.";

		} else if (text.contains("refund") || text.contains("money back")) {

			intent = AiIntent.REFUND_REQUEST;
			suggestedCategory = "REFUND";
			suggestedPriority = "MEDIUM";
			suggestedResponse = "We apologize for the delay with your refund. "
					+ "Our support team will review the refund status and provide you with an update.";

		} else if (text.contains("delivery") || text.contains("delivered") || text.contains("shipping")
				|| text.contains("shipment")) {

			intent = AiIntent.DELIVERY_ISSUE;
			suggestedCategory = "DELIVERY";
			suggestedPriority = "MEDIUM";
			suggestedResponse = "We understand your concern regarding the delivery. "
					+ "Our team will check the latest shipment status and provide an update.";

		} else if (text.contains("cancel")) {

			intent = AiIntent.ORDER_CANCELLATION;
			suggestedCategory = "ORDER";
			suggestedPriority = "MEDIUM";
			suggestedResponse = "Thank you for contacting support. "
					+ "Our team will review your request to cancel the order.";

		} else if (text.contains("return")) {

			intent = AiIntent.RETURN_REQUEST;
			suggestedCategory = "RETURN";
			suggestedPriority = "MEDIUM";
			suggestedResponse = "Thank you for contacting support. "
					+ "Our team will review your return request and provide instructions.";

		} else {

			intent = AiIntent.GENERAL_QUERY;
			suggestedCategory = "OTHER";
			suggestedPriority = "LOW";
			suggestedResponse = "Thank you for contacting support. "
					+ "Our team will review your request and get back to you.";
		}

		AiSentiment sentiment;
		AiUrgency urgency;

		if (text.contains("urgent") || text.contains("immediately") || text.contains("angry")
				|| text.contains("terrible")) {

			sentiment = AiSentiment.ANGRY;
			urgency = AiUrgency.CRITICAL;

		} else if (text.contains("frustrated") || text.contains("disappointed") || text.contains("bad")) {

			sentiment = AiSentiment.FRUSTRATED;
			urgency = AiUrgency.HIGH;

		} else {

			sentiment = AiSentiment.NEUTRAL;

			if (suggestedPriority.equals("HIGH")) {
				urgency = AiUrgency.HIGH;
			} else {
				urgency = AiUrgency.MEDIUM;
			}
		}

		String aiReasoning = "Ticket was analyzed based on customer subject and description.";

		return new AiAnalysisResult(intent, sentiment, urgency, suggestedCategory, suggestedPriority, aiReasoning, suggestedResponse);
	}

	public record AiAnalysisResult(
			AiIntent intent,
			AiSentiment sentiment,
			AiUrgency urgency,
			String suggestedCategory,
			String suggestedPriority,
			String aiReasoning,
			String suggestedResponse
	) {
	}
}