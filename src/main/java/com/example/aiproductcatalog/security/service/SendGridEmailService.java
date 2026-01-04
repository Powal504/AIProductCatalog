package com.example.aiproductcatalog.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Service
public class SendGridEmailService {

    private final WebClient webClient;
    private final String sendGridApiKey;
    private final String senderEmail;

    public SendGridEmailService(
            @Value("${sendgrid.api.key}") String sendGridApiKey,
            @Value("${support.email}") String senderEmail
    ) {
        this.sendGridApiKey = sendGridApiKey;
        this.senderEmail = senderEmail;
        this.webClient = WebClient.builder()
                .baseUrl("https://api.sendgrid.com/v3")
                .defaultHeader("Authorization", "Bearer " + sendGridApiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public void sendEmail(String to, String subject, String htmlContent) throws IOException {
        String jsonPayload = "{"
                + "\"personalizations\":[{\"to\":[{\"email\":\"" + to + "\"}]}],"
                + "\"from\":{\"email\":\"" + senderEmail + "\"},"
                + "\"subject\":\"" + subject + "\","
                + "\"content\":[{\"type\":\"text/html\",\"value\":\"" + htmlContent + "\"}]"
                + "}";

        try {
            webClient.post()
                    .uri("/mail/send")
                    .bodyValue(jsonPayload)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception e) {
            throw new IOException("Failed to send email: " + e.getMessage(), e);
        }
    }
}
