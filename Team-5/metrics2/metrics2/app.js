const express = require('express');
const client = require('prom-client');

// Create an Express application
const app = express();
const port = 3001;

// Create a Registry to register the metrics
const register = new client.Registry();

// Add a default set of metrics (e.g., Node.js process metrics, garbage collection, etc.)
client.collectDefaultMetrics({ register });

// Custom metrics
const taskFailureCounter = new client.Counter({
  name: 'task_failure_count',
  help: 'Total number of failed tasks',
});

const errorCounter = new client.Counter({
  name: 'error_count',
  help: 'Total number of errors',
});

const failureDuration = new client.Histogram({
  name: 'failure_duration_seconds',
  help: 'Duration of time between failures in seconds',
  buckets: [1, 2, 5, 10, 15, 30, 60, 120, 300], // Buckets for response time
});

const recoveryDuration = new client.Histogram({
  name: 'recovery_duration_seconds',
  help: 'Duration of time to recover from a failure in seconds',
  buckets: [1, 2, 5, 10, 15, 30, 60, 120, 300], // Buckets for recovery time
});

// Register the custom metrics
register.registerMetric(taskFailureCounter);
register.registerMetric(errorCounter);
register.registerMetric(failureDuration);
register.registerMetric(recoveryDuration);

// Simulate metrics collection
setInterval(() => {
  // Simulate a task failure and increase the counter
  if (Math.random() < 0.1) { // 10% chance of failure
    taskFailureCounter.inc();
  }

  // Simulate an error and increase the counter
  if (Math.random() < 0.05) { // 5% chance of error
    errorCounter.inc();
  }

  // Simulate failure duration
  if (Math.random() < 0.1) {
    const duration = Math.floor(Math.random() * 300); // Random duration between 0 and 300 seconds
    failureDuration.observe(duration);
  }

  // Simulate recovery duration
  if (Math.random() < 0.1) {
    const duration = Math.floor(Math.random() * 300); // Random duration between 0 and 300 seconds
    recoveryDuration.observe(duration);
  }

}, 1000); // Every second

// Expose the metrics endpoint at /metrics
app.get('/metrics', async (req, res) => {
  res.setHeader('Content-Type', register.contentType);
  res.send(await register.metrics());
});

// Start the server
app.listen(port, () => {
  console.log(`Dummy metrics app listening at http://localhost:${port}`);
});
