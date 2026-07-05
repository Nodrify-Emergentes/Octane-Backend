package nodrify.inc.octane.wellness.application.internal.outboundservices.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.genai.Client;

@Service
public class GenAIClient {

    private final Client client;
    private final String model;

    public GenAIClient(
            @Value("${ai.summary.api-key}") String apiKey,
            @Value("${ai.summary.model}") String model
    ) {
        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
        this.model = model;
    }

    public String generateSummary(String prompt) {
        var response = client.models.generateContent(model, prompt, null);

        // Extract text from the SDK response
        return response.text();
    }
}