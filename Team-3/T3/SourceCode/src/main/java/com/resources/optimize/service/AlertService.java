package com.resources.optimize.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

@Service
public class AlertService {
	@Autowired
	private MetricsAnalyzerService metricsAnalyzerService;
   
	 public void checkAndAlert(Map<String, List<Map<String, Object>>> metrics) {
	        // Check CPU Usage
	        List<Map<String, Object>> cpuMetrics = metrics.get("system.cpu.usage");
	        if (cpuMetrics != null) {
	            OptionalDouble cpuUsage = metricsAnalyzerService.analyzeUsage(cpuMetrics);
	            cpuUsage.ifPresent(usage -> {
	                if (usage > 0.9) {
	                    sendAlert("High CPU Usage: " + String.format("%.2f", usage * 100) + "%");
	                }
	            });
	        }

	        // Check Memory Usage
	        List<Map<String, Object>> memoryMetrics = metrics.get("jvm.memory.use");
	        if (memoryMetrics != null) {
	            OptionalDouble memoryUsage = metricsAnalyzerService.analyzeMemoryUsage(memoryMetrics);
	            memoryUsage.ifPresent(usage -> {
	                if (usage > 0.8) { // Example threshold for memory usage
	                    sendAlert("High Memory Usage: " + String.format("%.2f", usage * 100) + "%");
	                }
	            });
	        }
	    }

	    private void sendAlert(String message) {
	        System.out.println("ALERT: " + message); 
	    }
}