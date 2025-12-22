package com.example.aiproductcatalog.web.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Map;

@Service
public class SentimentClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String ML_API_URL = "http://localhost:8000/predict";

    public Map<String, Object> analyzeSentiment(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = String.format("{\"text\": \"%s\"}", text.replace("\"", "'"));
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(ML_API_URL, request, Map.class);
        return response.getBody();
    }
}

