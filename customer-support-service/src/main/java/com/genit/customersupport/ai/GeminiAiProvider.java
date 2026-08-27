package com.genit.customersupport.ai;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.genit.customersupport.config.GeminiConfig;
import com.genit.customersupport.dto.AiAnalysisResponse;
import com.genit.customersupport.dto.GeminiRequest;
import com.genit.customersupport.dto.GeminiResponse;

import tools.jackson.databind.ObjectMapper;

@Component
public class GeminiAiProvider implements AiProvider {

	private final RestClient restClient;
	private final GeminiConfig geminiConfig;
	private final ObjectMapper objectMapper;

	private static final String MODEL = "gemini-3.6-flash";

	public GeminiAiProvider(RestClient geminiRestClient, GeminiConfig geminiConfig, ObjectMapper objectMapper) {

		this.restClient = geminiRestClient;
		this.geminiConfig = geminiConfig;
		this.objectMapper = objectMapper;
	}

	@Override
	public AiAnalysisResponse analyzeTicket(String subject, String description) {

		String prompt = buildPrompt(subject, description);

		GeminiRequest.Part part = new GeminiRequest.Part(prompt);

		GeminiRequest.Content content = new GeminiRequest.Content(List.of(part));

		GeminiRequest request = new GeminiRequest(List.of(content));

		GeminiResponse response = restClient.post()
				.uri(uriBuilder -> uriBuilder.path("/v1beta/models/" + MODEL + ":generateContent")
						.queryParam("key", geminiConfig.getApiKey()).build())
				.body(request).retrieve().body(GeminiResponse.class);

		return parseGeminiResponse(response);
	}

	private String buildPrompt(String subject, String description) {

		return """
				You are an AI customer support ticket analyzer.

				Analyze the following customer support ticket.

				Subject:
				%s

				Description:
				%s


				================================
				STRICT OUTPUT REQUIREMENTS
				================================

				Return EXACTLY ONE valid JSON object.

				Do NOT return a JSON array.

				Do NOT wrap the JSON object inside [ ].

				Do NOT use Markdown.

				Do NOT use ```json.

				Do NOT add any explanation before or after the JSON.

				Return only the JSON object.


				================================
				REQUIRED JSON STRUCTURE
				================================

				{
				  "intent": "ORDER_DELAY",
				  "sentiment": "FRUSTRATED",
				  "urgency": "HIGH",
				  "suggestedCategory": "DELIVERY",
				  "suggestedPriority": "HIGH",
				  "summary": "Short one sentence summary of the customer's issue.",
				  "reasoning": "Short explanation for the classification.",
				  "suggestedResponse": "Polite customer support response."
				}


				================================
				STRICT ENUM RULES
				================================

				The "intent" value MUST be exactly one of these values:

				OTHER
				GENERAL_QUERY
				ORDER_DELAY
				ORDER_CANCELLATION
				ACCOUNT_ISSUE
				REFUND_REQUEST
				PRODUCT_ISSUE
				DELIVERY_ISSUE
				PAYMENT_ISSUE
				TECHNICAL_ISSUE
				RETURN_REQUEST

				NEVER invent a new intent value.

				NEVER use values such as:

				DELIVERY_DELAY
				DELIVERY_LATE
				LATE_DELIVERY
				SHIPMENT_DELAY
				PACKAGE_DELAY

				If the customer is complaining that an order/package/delivery
				is delayed or has not arrived on time, use:

				ORDER_DELAY


				If the customer has a general delivery-related problem that
				is NOT specifically an order delay, use:

				DELIVERY_ISSUE


				================================
				SENTIMENT VALUES
				================================

				The "sentiment" value MUST be exactly one of:

				NEUTRAL
				POSITIVE
				FRUSTRATED
				ANGRY


				================================
				URGENCY VALUES
				================================

				The "urgency" value MUST be exactly one of:

				LOW
				MEDIUM
				HIGH
				URGENT


				================================
				PRIORITY VALUES
				================================

				The "suggestedPriority" value MUST be exactly one of:

				LOW
				MEDIUM
				HIGH
				URGENT


				================================
				CATEGORY VALUES
				================================

				The "suggestedCategory" value MUST be exactly one of:

				PAYMENT
				REFUND
				DELIVERY
				RETURN
				ORDER
				ACCOUNT
				TECHNICAL
				OTHER


				================================
				FIELD REQUIREMENTS
				================================

				The JSON MUST contain exactly these fields:

				intent
				sentiment
				urgency
				suggestedCategory
				suggestedPriority
				summary
				reasoning
				suggestedResponse


				================================
				FINAL VALIDATION
				================================

				Before returning the answer, verify:

				1. The response is ONE JSON object.
				2. The response is NOT an array.
				3. "intent" uses only the allowed intent values.
				4. A delayed order/delivery uses ORDER_DELAY.
				5. "sentiment" uses only the allowed sentiment values.
				6. "urgency" uses only the allowed urgency values.
				7. "suggestedPriority" uses only the allowed priority values.
				8. "suggestedCategory" uses only the allowed category values.
				9. All required fields are present.
				10. No Markdown or additional text is returned.

				Return ONLY the JSON object.

				""".formatted(subject, description);
	}

	private AiAnalysisResponse parseGeminiResponse(GeminiResponse response) {

		if (response == null || response.getCandidates() == null || response.getCandidates().isEmpty()
				|| response.getCandidates().get(0).getContent() == null
				|| response.getCandidates().get(0).getContent().getParts() == null
				|| response.getCandidates().get(0).getContent().getParts().isEmpty()) {

			throw new IllegalStateException("Gemini returned an empty response");
		}

		String json = response.getCandidates().get(0).getContent().getParts().get(0).getText();

		if (json == null || json.isBlank()) {

			throw new IllegalStateException("Gemini returned empty text");
		}

		json = cleanGeminiJson(json);

		try {

			return objectMapper.readValue(json, AiAnalysisResponse.class);

		} catch (Exception ex) {

			throw new IllegalStateException("Failed to parse Gemini AI response: " + json, ex);
		}
	}

	private String cleanGeminiJson(String json) {

		json = json.trim();

		// Remove Markdown code fences if Gemini returns them
		if (json.startsWith("```json")) {
			json = json.substring(7).trim();
		} else if (json.startsWith("```")) {
			json = json.substring(3).trim();
		}

		if (json.endsWith("```")) {
			json = json.substring(0, json.length() - 3).trim();
		}

		return json;
	}
}