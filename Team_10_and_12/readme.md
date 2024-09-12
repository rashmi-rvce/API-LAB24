# System Health Monitoring Tool

## Overview

The **System Health Monitoring Tool** is a Python-based application designed to monitor and evaluate system health and performance. This tool provides real-time insights into CPU usage, memory consumption, disk utilization, and network activity. Additionally, it can generate random numbers with a controlled delay to simulate workload conditions, allowing for the assessment of system performance under stress.

## Features

- **CPU Usage Monitoring:** Tracks and reports the percentage of CPU utilization.
- **Memory Monitoring:** Provides details on total, used, and available memory.
- **Disk Usage Monitoring:** Reports on disk space usage, including total, used, and free space.
- **Network Monitoring:** Displays bytes sent and received over the network.
- **Workload Simulation:** Generates random numbers with a specified delay to test system performance under load.

## Installation

### Prerequisites

- **Python**: Version 3.8 or higher. [Download Python](https://www.python.org/downloads/)
- **psutil**: Python library for retrieving system performance metrics. Install via pip.

### Steps

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/Savitha11111/-System-Health-Monitoring-Tool-.git
   ```
2. **Navigate to the Project Directory:**
   ```bash
   cd System-Health-Monitoring-Tool
   ```
3. **Install Dependencies:**
   ```bash
   pip install psutil
   ```

## Usage

1. **Run the Application:**
   Execute the main script to start monitoring system health and generating random numbers:
   ```bash
   python main.py
   ```

2. **Configure Parameters:**
   Modify the `main.py` script to adjust parameters such as the number of random numbers to generate, the delay between generations, and the frequency of health checks.

## Code Structure

- **`main.py`**: The main script that initiates the health monitoring and random number generation.
- **`utils.py`**: Contains utility functions for formatting time and performing health checks.
- **`requirements.txt`**: Lists the Python packages required for the project.

## Contributing

Contributions are welcome! If you have suggestions or improvements, please fork the repository and create a pull request. For significant changes, please open an issue to discuss your proposed changes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- **psutil**: For providing a cross-platform library to retrieve system performance metrics.



