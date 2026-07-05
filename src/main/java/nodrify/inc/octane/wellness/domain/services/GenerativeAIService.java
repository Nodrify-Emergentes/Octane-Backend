package nodrify.inc.octane.wellness.domain.services;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import nodrify.inc.octane.wellness.application.internal.outboundservices.ai.GenerativeAIClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GenerativeAIService {
    private final Client client;

    @Value("${ai.summary.model}")
    private String model;

    private GenerativeAIService(GenerativeAIClient client) {
        this.client = client.configure();
    }

    public String generateSummary(String prompt) {
        GenerateContentResponse response =
                client.models.generateContent(
                        model,
                        prompt,
                        null);

        // Extract text from the SDK response
        return response.text();
    }
}