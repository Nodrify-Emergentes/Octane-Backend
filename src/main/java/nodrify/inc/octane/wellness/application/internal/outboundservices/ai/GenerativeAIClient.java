package nodrify.inc.octane.wellness.application.internal.outboundservices.ai;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenerativeAIClient {
    @Value("${ai.summary.api-key}")
    private String apiKey;

    public Client configure() {
        return Client.builder().apiKey(apiKey).build();
    }
}
