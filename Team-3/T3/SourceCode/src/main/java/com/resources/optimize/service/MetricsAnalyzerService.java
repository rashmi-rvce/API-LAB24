package com.resources.optimize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class MetricsAnalyzerService {
    @Autowired
    private RestTemplate restTemplate;
	 
    public OptionalDouble analyzeUsage(List<Map<String, Object>> metricsList) {
        return metricsList.stream()
            .flatMap(m -> {
                // Print the map structure
                System.out.println("Processing Map: " + m);

                // Access the measurements list
                List<Map<String, Object>> measurements = (List<Map<String, Object>>) m.get("measurements");
                return measurements.stream();
            })
            .peek(m -> {
                // Print each measurement map
                System.out.println("Measurement Map: " + m);

                // Print the value inside the measurement map
                Object valueObj = m.get("value");
                System.out.println("Value Object: " + valueObj);
            })
            .mapToDouble(m -> {
                Object valueObj = m.get("value");
                return valueObj instanceof Number ? ((Number) valueObj).doubleValue() : 0.0;
            })
            .max(); // Terminal operation
    }
    
    public OptionalDouble analyzeMemoryUsage(List<Map<String, Object>> metricsList) {
        
    	return metricsList.stream()
                .filter(m -> m.containsKey("measurements"))
                .flatMap(m -> ((List<Map<String, Object>>) m.get("measurements")).stream())
                .mapToDouble(m -> {
                    Object valueObj = m.get("value");
                    return valueObj instanceof Number ? ((Number) valueObj).doubleValue() : 0.0;
                })
                .average(); // Calculate the average value
        }

	    public ResponseEntity<Map<String, List<Map<String, Object>>>> fetchMetrics() {
	        String targetAppHealthUrl = "http://localhost:8080/all";
	        
	        // Fetch metrics from the remote URL
	        ResponseEntity<Map<String, List<Map<String, Object>>>> responseEntity =
	                restTemplate.getForEntity(targetAppHealthUrl, (Class<Map<String, List<Map<String, Object>>>>) (Class<?>) Map.class);

	        return responseEntity;
	    }
	    
	    public OptionalDouble analyzeDisk(List<Map<String, Object>> metricsList) {
	        return metricsList.stream()
	            .flatMap(m -> {
	                // Access the measurements list
	                List<Map<String, Object>> measurements = (List<Map<String, Object>>) m.get("measurements");
	                return measurements.stream();
	            })
	            .peek(m -> {
	                // Print each measurement map
	                System.out.println("Measurement Map: " + m);

	                // Print the value inside the measurement map
	                Object valueObj = m.get("value");
	                System.out.println("Value Object: " + valueObj);
	            })
	            .mapToDouble(m -> {
	                Object valueObj = m.get("value");
	                return valueObj instanceof Number ? ((Number) valueObj).doubleValue() : 0.0;
	            })
	            .max(); // Terminal operation
	    }
	    
	    public Map<String, RequestMetrics> analyzeRequests(List<Map<String, Object>> metricsList) {
	        return metricsList.stream()
	                .collect(Collectors.groupingBy(
	                    m -> (String) ((Map<String, Object>) m.get("tags")).get("uri"),
	                    Collectors.collectingAndThen(
	                        Collectors.toList(),
	                        list -> {
	                            double totalRequests = list.size();
	                            double totalTime = list.stream()
	                                    .flatMap(m -> ((List<Map<String, Object>>) m.get("measurements")).stream())
	                                    .filter(m -> "TOTAL_TIME".equals(m.get("statistic")))
	                                    .mapToDouble(m -> ((Number) m.get("value")).doubleValue())
	                                    .sum();
	                            double maxTime = list.stream()
	                                    .flatMap(m -> ((List<Map<String, Object>>) m.get("measurements")).stream())
	                                    .filter(m -> "MAX".equals(m.get("statistic")))
	                                    .mapToDouble(m -> ((Number) m.get("value")).doubleValue())
	                                    .max()
	                                    .orElse(0.0);
	                            return new RequestMetrics(totalRequests, totalTime, maxTime);
	                        }
	                    )
	                ));
	    }

	    public static class RequestMetrics {
	        private final double totalRequests;
	        private final double totalTime;
	        private final double maxTime;

	        public RequestMetrics(double totalRequests, double totalTime, double maxTime) {
	            this.totalRequests = totalRequests;
	            this.totalTime = totalTime;
	            this.maxTime = maxTime;
	        }

	        public double getTotalRequests() {
	            return totalRequests;
	        }

	        public double getTotalTime() {
	            return totalTime;
	        }

	    }

		
	}


