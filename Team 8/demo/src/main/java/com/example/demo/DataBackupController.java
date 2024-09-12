package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequestMapping("/api/backup")
public class DataBackupController {

    @PostMapping
    public ResponseEntity<String> backupDataPost(@RequestParam String sourceFilePath, @RequestParam String backupFilePath) {
        return backupData(sourceFilePath, backupFilePath);
    }

    @GetMapping
    public ResponseEntity<String> backupDataGet(@RequestParam String sourceFilePath, @RequestParam String backupFilePath) {
        return backupData(sourceFilePath, backupFilePath);
    }

    private ResponseEntity<String> backupData(String sourceFilePath, String backupFilePath) {
        File sourceFile = new File(sourceFilePath);
        File backupFile = new File(backupFilePath);

        // Ensure the source file exists
        if (!sourceFile.exists()) {
            return ResponseEntity.status(404).body("Source file does not exist: " + sourceFilePath);
        }

        // Ensure the backup directory exists
        File backupDir = backupFile.getParentFile();
        if (backupDir != null && !backupDir.exists()) {
            if (!backupDir.mkdirs()) {
                return ResponseEntity.status(500).body("Failed to create backup directory: " + backupDir.getAbsolutePath());
            }
        }

        // Perform the backup
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(backupFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush(); // Ensure all data is written to the file

            return ResponseEntity.ok("Backup completed successfully!");

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error during backup: " + e.getMessage());
        }
    }
}
