package com.resources.optimize.controller;


import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resources.optimize.service.MetricsAnalyzerService;
import com.resources.optimize.service.MetricsAnalyzerService.RequestMetrics;
import com.resources.optimize.service.TargetAppMetricsService;

@RestController
@RequestMapping("/api")
public class TargetHealthController {

    @Autowired
    public TargetAppMetricsService targetAppMetricsService;
    
    @Autowired
    public MetricsAnalyzerService metricsAnalyzerService;

    @GetMapping("/health")
    public String getTargetAppHealth() {
        return targetAppMetricsService.fetchHealthStatus();
    }
    
    
    @GetMapping("/cpu-usage")
    public ResponseEntity<String> getCpuUsage() {
        try {
            // Fetch metrics from the service
            ResponseEntity<Map<String, List<Map<String, Object>>>> responseEntity = metricsAnalyzerService.fetchMetrics();

            // Extract the metrics list for CPU usage
            Map<String, List<Map<String, Object>>> metricsMap = responseEntity.getBody();
            if (metricsMap == null || !metricsMap.containsKey("process.cpu.usage")) {
                return ResponseEntity.status(404).body("CPU usage metrics not found.");
            }

            List<Map<String, Object>> cpuMetrics = metricsMap.get("process.cpu.usage");

            // Analyze CPU usage and return it
            OptionalDouble avgCpuUsage = metricsAnalyzerService.analyzeUsage(cpuMetrics);
            double result = avgCpuUsage.orElse(0.0);
            result=result*100;
            String formattedValue = String.format("%.3f", result);
            return ResponseEntity.ok("Average CPU usage: " + formattedValue +" % " );
        } catch (Exception e) {
            // Log the error and return an error response
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while processing metrics.");
        }
    }
    
    @GetMapping("/memory-usage")
    public ResponseEntity<String> getMemoryUsage() {
    	 try {
             // Fetch metrics from the service
             ResponseEntity<Map<String, List<Map<String, Object>>>> responseEntity = metricsAnalyzerService.fetchMetrics();

             // Extract the metrics list for memory usage
             Map<String, List<Map<String, Object>>> metricsMap = responseEntity.getBody();
             if (metricsMap == null || !metricsMap.containsKey("jvm.memory.used")) {
                 return ResponseEntity.status(404).body("Memory usage metrics not found.");
             }

             List<Map<String, Object>> jvmMetrics = metricsMap.get("jvm.memory.used");

             // Analyze memory usage and return it
             OptionalDouble avgMemoryUsage = metricsAnalyzerService.analyzeMemoryUsage(jvmMetrics);
             double result = avgMemoryUsage.orElse(0.0);
             result = result / (1024 * 1024); // Convert from bytes to megabytes
             String formattedValue = String.format("%.3f", result);
             return ResponseEntity.ok("Average Memory usage: " + formattedValue + " MB");
         } catch (Exception e) {
             // Log the error and return an error response
             e.printStackTrace();
             return ResponseEntity.status(500).body("An error occurred while processing metrics.");
         }
     }

    @GetMapping("/disktotal")
    public ResponseEntity<String> getDiskTotal() {
        try {
            ResponseEntity<Map<String, List<Map<String, Object>>>> responseEntity = metricsAnalyzerService.fetchMetrics();

            Map<String, List<Map<String, Object>>> metricsMap = responseEntity.getBody();
            if (metricsMap == null || !metricsMap.containsKey("disk.total")) {
                return ResponseEntity.status(404).body("Disk metrics not found.");
            }

            List<Map<String, Object>> diskMetrics = metricsMap.get("disk.total");

            OptionalDouble size = metricsAnalyzerService.analyzeUsage(diskMetrics);
            double result = size.orElse(0.0);
            result=result/(1024*1024*1024);
            String formattedValue = String.format("%.3f", result);
            return ResponseEntity.ok("Total Diskspace is: " + formattedValue +" GB " );
        } catch (Exception e) {
            // Log the error and return an error response
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while processing metrics.");
        }
    }
    
    @GetMapping("/diskfree")
    public ResponseEntity<String> getDiskFree() {
        try {
            ResponseEntity<Map<String, List<Map<String, Object>>>> responseEntity = metricsAnalyzerService.fetchMetrics();

            Map<String, List<Map<String, Object>>> metricsMap = responseEntity.getBody();
            if (metricsMap == null || !metricsMap.containsKey("disk.free")) {
                return ResponseEntity.status(404).body("Disk metrics not found.");
            }

            List<Map<String, Object>> diskMetrics = metricsMap.get("disk.free");

            OptionalDouble size = metricsAnalyzerService.analyzeUsage(diskMetrics);
            double result = size.orElse(0.0);
            result=result/(1024*1024*1024);
            String formattedValue = String.format("%.3f", result);
            return ResponseEntity.ok("Free Diskspace is: " + formattedValue +" GB " );
        } catch (Exception e) {
            // Log the error and return an error response
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while processing metrics.");
        }
    }
    
    @GetMapping("/apptime")
    public ResponseEntity<String> getAppTime() {
        try {
            ResponseEntity<Map<String, List<Map<String, Object>>>> responseEntity = metricsAnalyzerService.fetchMetrics();

            Map<String, List<Map<String, Object>>> metricsMap = responseEntity.getBody();
            if (metricsMap == null || !metricsMap.containsKey("application.started.time")) {
                return ResponseEntity.status(404).body("App metrics not found.");
            }

            List<Map<String, Object>> appMetrics = metricsMap.get("application.started.time");

            OptionalDouble size = metricsAnalyzerService.analyzeUsage(appMetrics);
            double result = size.orElse(0.0);
            String formattedValue = String.format("%.3f", result);
            return ResponseEntity.ok("Time required to start the application is: " + formattedValue +" sec " );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while processing metrics.");
        }
    }
    
    @GetMapping("/tomcat")
    public ResponseEntity<String> getSessions() {
        try {
            ResponseEntity<Map<String, List<Map<String, Object>>>> responseEntity = metricsAnalyzerService.fetchMetrics();

            Map<String, List<Map<String, Object>>> metricsMap = responseEntity.getBody();
            if (metricsMap == null || !metricsMap.containsKey("tomcat.sessions.active.current")) {
                return ResponseEntity.status(404).body("Session metrics not found.");
            }

            List<Map<String, Object>> sessionMetrics = metricsMap.get("tomcat.sessions.active.current");

            OptionalDouble size = metricsAnalyzerService.analyzeUsage(sessionMetrics);
            double result = size.orElse(0.0);
            String formattedValue = String.format("%.3f", result);
            return ResponseEntity.ok("Current Server Session is: " + formattedValue);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while processing metrics.");
        }
    }
    
    @GetMapping("/httpreqs")
    public ResponseEntity<String> getRequests() {
        try {
            // Fetch metrics from the service
            ResponseEntity<Map<String, List<Map<String, Object>>>> responseEntity = metricsAnalyzerService.fetchMetrics();

            // Extract the metrics list for HTTP requests
            Map<String, List<Map<String, Object>>> metricsMap = responseEntity.getBody();
            if (metricsMap == null || !metricsMap.containsKey("http.server.requests")) {
                return ResponseEntity.status(404).body("HTTP metrics not found.");
            }

            List<Map<String, Object>> httpMetrics = metricsMap.get("http.server.requests");

            // Analyze the request metrics
            Map<String, RequestMetrics> requestMetricsMap = metricsAnalyzerService.analyzeRequests(httpMetrics);

            // Format the output for each endpoint
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, RequestMetrics> entry : requestMetricsMap.entrySet()) {
                String endpoint = entry.getKey();
                RequestMetrics metrics = entry.getValue();
                sb.append(String.format("Endpoint: %s\n", endpoint))
                  .append(String.format("Method: GET\n")) // Assuming GET for simplicity
                  .append(String.format("Status: 200 OK\n")) // Assuming 200 OK for simplicity
                  .append(String.format("Outcome: SUCCESS\n")) // Assuming SUCCESS for simplicity
                  .append(String.format("Total Requests: %.0f\n", metrics.getTotalRequests()))
                  .append(String.format("Total Time Taken: %.2f seconds\n", metrics.getTotalTime()))
                  .append("\n");
            }

            return ResponseEntity.ok(sb.toString());
        } catch (Exception e) {
            // Log the error and return an error response
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while processing metrics.");
        }
    }

}
