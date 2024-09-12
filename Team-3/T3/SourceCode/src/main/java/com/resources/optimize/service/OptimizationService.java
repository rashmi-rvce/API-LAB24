package com.resources.optimize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

@RestController
@RequestMapping("/optimize")
@Service
public class OptimizationService {

	@Autowired
	private MetricsAnalyzerService metricsAnalyzerService;

	@GetMapping("/cpu")
	public ResponseEntity<String> optimizeCpuUsage() {
		try {
			ResponseEntity<Map<String, List<Map<String, Object>>>> responseEntity = metricsAnalyzerService
					.fetchMetrics();

			Map<String, List<Map<String, Object>>> metricsMap = responseEntity.getBody();
			if (metricsMap == null || !metricsMap.containsKey("process.cpu.usage")) {
				return ResponseEntity.status(404).body("CPU usage metrics not found.");
			}

			List<Map<String, Object>> cpuMetrics = metricsMap.get("process.cpu.usage");
			if (cpuMetrics == null || cpuMetrics.isEmpty()) {
				return ResponseEntity.status(404).body("CPU metrics not found.");
			}

			OptionalDouble avgCpuUsage = metricsAnalyzerService.analyzeUsage(cpuMetrics);
			if (avgCpuUsage.isPresent()) {
				double cpuUsage = avgCpuUsage.getAsDouble();

				if (cpuUsage > 0.9) {
					optimizeCpu();
					return ResponseEntity.ok("CPU usage was high (" + String.format("%.2f", cpuUsage * 100)
							+ "%). Optimization applied.");
				} else {
					return ResponseEntity.ok("CPU usage is within acceptable limits ("
							+ String.format("%.2f", cpuUsage * 100) + "%). No optimization needed.");
				}
			} else {
				return ResponseEntity.status(500).body("Failed to analyze CPU metrics.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("An error occurred while processing metrics.");
		}
	}

	private void optimizeCpu() {
	    System.out.println("Optimizing CPU usage...");

	    try {
	        // Step 1: Find the PID of the process running on port 8080
	        String findPidCommand = "netstat -aon | findstr :8080";
	        Process findPidProcess = Runtime.getRuntime().exec(findPidCommand);
	        BufferedReader reader = new BufferedReader(new InputStreamReader(findPidProcess.getInputStream()));
	        String line;
	        String pid = null;
	        
	        while ((line = reader.readLine()) != null) {
	            // The last column of the netstat output contains the PID
	            pid = line.trim().split("\\s+")[4];
	            break; // Assume there's only one process listening on the port
	        }
	        findPidProcess.waitFor();
	        
	        if (pid != null) {
	            // Step 2: Kill the process with the found PID
	            String killCommand = "taskkill /PID " + pid + " /F";
	            Process killProcess = Runtime.getRuntime().exec(killCommand);
	            killProcess.waitFor();
	            System.out.println("Process with PID " + pid + " has been terminated.");

	            // Step 3: Restart the application
	            String startCommand = "cmd /c start java -jar C:\\path\\to\\your-application.jar"; // Adjust path accordingly
	            Process startProcess = Runtime.getRuntime().exec(startCommand);
	            startProcess.waitFor();
	            System.out.println("Application has been restarted.");

	        } else {
	            System.out.println("No process found running on port 8080.");
	        }

	    } catch (IOException | InterruptedException e) {
	        e.printStackTrace();
	        System.out.println("Failed to restart the application.");
	    }
	}

	
	@GetMapping("/memory")
	public ResponseEntity<String> optimizeMemoryUsage() {
		 try {
	            // Fetch metrics
	            ResponseEntity<Map<String, List<Map<String, Object>>>> responseEntity = metricsAnalyzerService.fetchMetrics();

	            // Extract memory usage metrics
	            Map<String, List<Map<String, Object>>> metricsMap = responseEntity.getBody();
	            if (metricsMap == null || !metricsMap.containsKey("jvm.memory.used")) {
	                return ResponseEntity.status(404).body("Memory usage metrics not found.");
	            }

	            List<Map<String, Object>> memoryMetrics = metricsMap.get("jvm.memory.used");
	            if (memoryMetrics == null || memoryMetrics.isEmpty()) {
	                return ResponseEntity.status(404).body("Memory metrics not found.");
	            }

	            // Analyze memory usage
	            OptionalDouble memoryUsage = metricsAnalyzerService.analyzeMemoryUsage(memoryMetrics);
	            if (memoryUsage.isPresent()) {
	            	double result = memoryUsage.orElse(0.0);
	                result = result / (1024 * 1024); // Convert from bytes to megabytes
	                String formattedValue = String.format("%.3f", result);

	                if (result > 80) { 
	                    optimizeMemory();
	                    return ResponseEntity.ok(" Optimization is need Memory usage is greater than 80 %");
	                } else {
	                    return ResponseEntity.ok("Memory usage is normal : " + formattedValue + " MB");
	                }
	            } else {
	                return ResponseEntity.status(500).body("Failed to analyze memory metrics.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).body("An error occurred while processing metrics.");
	        }
	    }

	    private void optimizeMemory() {
	        System.out.println("Optimizing memory usage...");

	        // Example: Trigger garbage collection
	        System.gc();
	    }
	    
	    @GetMapping("/temp")
	    public ResponseEntity<String> cleanTempFiles() {
	        try {
	            // Get the system temporary directory
	            Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));

	            // Walk the file tree and delete all files and sub-directories
	            Files.walk(tempDir)
	                .sorted(Comparator.reverseOrder())
	                .map(Path::toFile)
	                .forEach(file -> {
	                    if (file.exists()) {
	                        boolean result = file.delete();
	                        if (result) {
	                            System.out.println("Successfully deleted: " + file.getName());
	                        } else {
	                            System.out.println("Could not delete: " + file.getName());
	                        }
	                    }
	                });

	            return ResponseEntity.ok("Temporary files cleaned successfully.");
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).body("An error occurred while cleaning temporary files.");
	        }
	    }
}