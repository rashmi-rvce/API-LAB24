MERN Stack Hotel Booking System with Prometheus and Grafana Monitoring
Project Overview
This is a full-stack MERN (MongoDB, Express, React, Node.js) application designed to serve as a hotel booking system. Users can search, book rooms, and view their reservations. The backend, built with Node.js and Express, handles API requests, manages bookings, and interacts with MongoDB to store data.

To monitor the performance and health of the backend, we have integrated Prometheus for metrics collection and Grafana for visualizing these metrics. The system is instrumented to track metrics such as task failures, error rates, and recovery times to ensure the reliability and stability of the application.

Features
Frontend: Built with React for an interactive user interface.
Backend: Built with Express.js and Node.js to handle API requests.
Database: MongoDB for storing booking and reservation data.
Metrics Collection: Prometheus scrapes performance metrics exposed by the backend.
Visualization: Grafana visualizes the collected metrics in customizable dashboards.
Tools Used
MongoDB: NoSQL database for storing hotel and booking data.
Express.js: Web framework for building the backend API.
React.js: JavaScript library for creating the frontend user interface.
Node.js: JavaScript runtime for executing server-side code.
Prometheus: Monitoring tool for collecting time-series metrics.
Grafana: Data visualization platform for Prometheus metrics.
Getting Started
Prerequisites
Before running the application, ensure the following are installed:

Node.js (v14 or later)
MongoDB
Prometheus
Grafana
Installation
Clone the repository:

bash
Copy code
git clone https://github.com/your-repo/hotel-booking-system.git
cd hotel-booking-system
Install backend dependencies:

bash
Copy code
cd backend
npm install
Install frontend dependencies:

bash
Copy code
cd ../frontend
npm install
Start MongoDB server:

bash
Copy code
mongod
Run the backend server:

bash
Copy code
cd backend
npm start
Run the frontend server:

bash
Copy code
cd ../frontend
npm start
The frontend will run on http://localhost:3000 and the backend API will run on http://localhost:3001.

Instrumenting the Backend for Prometheus
To monitor the performance of the backend, we use the prom-client library for Node.js.

Install the prom-client package:

bash
Copy code
npm install prom-client
In your backend code, expose the /metrics endpoint as shown below:

javascript
Copy code
const express = require('express');
const client = require('prom-client');

const app = express();
const register = new client.Registry();

// Define metrics
const taskFailureCounter = new client.Counter({ name: 'task_failure_count', help: 'Total number of failed tasks' });
const errorCounter = new client.Counter({ name: 'error_count', help: 'Total number of errors' });
const failureDuration = new client.Histogram({ name: 'failure_duration_seconds', help: 'Time between failures' });
const recoveryDuration = new client.Histogram({ name: 'recovery_duration_seconds', help: 'Time to recover from failure' });

// Register metrics
register.registerMetric(taskFailureCounter);
register.registerMetric(errorCounter);
register.registerMetric(failureDuration);
register.registerMetric(recoveryDuration);

// Expose /metrics endpoint
app.get('/metrics', async (req, res) => {
  res.setHeader('Content-Type', register.contentType);
  res.send(await register.metrics());
});

app.listen(3001, () => {
  console.log('App running on port 3001');
});
Add the application as a target in Prometheus by editing the prometheus.yml file:

yaml
Copy code
scrape_configs:
  - job_name: 'mern_application'
    static_configs:
      - targets: ['localhost:3001']  # Application's metrics endpoint
Start Prometheus:

bash
Copy code
prometheus --config.file=prometheus.yml
Verify the /metrics endpoint by visiting:

bash
Copy code
http://localhost:3001/metrics
Configuring Grafana
Start Grafana:

bash
Copy code
grafana-server
Access Grafana at http://localhost:3000 and configure Prometheus as a data source:

Navigate to Configuration -> Data Sources -> Add data source.
Select Prometheus and configure it to point to your Prometheus server (e.g., http://localhost:9090).
Create a custom dashboard for metrics such as:

Task Failure Rate
Error Rate
MTBF (Mean Time Between Failures)
MTTR (Mean Time to Recovery)
Example PromQL queries:

Task Failure Rate: rate(task_failure_count[5m])
Error Rate: rate(error_count[5m])
MTBF: histogram_quantile(0.95,sum(rate(failure_duration_seconds_bucket[5m])) by (le))
MTTR: histogram_quantile(0.95,sum(rate(recovery_duration_seconds_bucket[5m])) by (le))