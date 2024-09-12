package com.resources.optimize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class MetricsCollectorService {
    private final RestTemplate restTemplate;
    private final String metricsUrl = "http://localhost:8080/all";

    @Autowired
    public MetricsCollectorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 60000) // Collect every minute
    public void collectMetrics() {
        Map<String, Object> metrics = restTemplate.getForObject(metricsUrl, Map.class);
        // Process and store the metrics
    }
}