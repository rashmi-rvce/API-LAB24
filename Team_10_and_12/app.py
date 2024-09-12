import time
import random
import psutil

def format_time(total_seconds):
    """
    Convert total seconds to a string in hr:mm:ss:ms format.
    
    Parameters:
    - total_seconds: Total time in seconds
    
    Returns:
    - Formatted time string
    """
    hours, remainder = divmod(total_seconds, 3600)
    minutes, seconds = divmod(remainder, 60)
    seconds, milliseconds = divmod(seconds * 1000, 1000)
    return f"{int(hours):02}:{int(minutes):02}:{int(seconds):02}:{int(milliseconds):03}"

def collect_performance_metrics():
    """
    Collect and print performance metrics related to CPU, memory, disk, and network.
    """
    print("Performance Metrics:")
    
    # Collect CPU usage
    cpu_usage = psutil.cpu_percent(interval=1)
    
    # Collect memory usage
    memory = psutil.virtual_memory()
    available_memory_mb = memory.available / (1024 ** 2)  # MB
    used_memory_mb = memory.used / (1024 ** 2)  # MB
    total_memory_mb = memory.total / (1024 ** 2)  # MB
    
    # Collect disk usage
    disk = psutil.disk_usage('/')
    disk_total_gb = disk.total / (1024 ** 3)  # GB
    disk_used_gb = disk.used / (1024 ** 3)  # GB
    disk_free_gb = disk.free / (1024 ** 3)  # GB

    # Collect network stats
    network = psutil.net_io_counters()
    bytes_sent_mb = network.bytes_sent / (1024 ** 2)  # MB
    bytes_recv_mb = network.bytes_recv / (1024 ** 2)  # MB

    # Print collected metrics
    print(f"CPU Usage: {cpu_usage:.2f}%")
    print(f"Memory - Total: {total_memory_mb:.2f} MB, Used: {used_memory_mb:.2f} MB, Available: {available_memory_mb:.2f} MB")
    print(f"Disk - Total: {disk_total_gb:.2f} GB, Used: {disk_used_gb:.2f} GB, Free: {disk_free_gb:.2f} GB, Usage: {disk.percent}%")
    print(f"Network - Bytes Sent: {bytes_sent_mb:.2f} MB, Bytes Received: {bytes_recv_mb:.2f} MB")

def health_check():
    """
    Perform a health check on CPU usage, memory, disk usage, and network stats.
    
    Returns:
    - A dictionary with the results of the health check or None if there are issues.
    """
    print("Performing health check...")

    health_stats = {}

    # Check CPU usage
    cpu_usage = psutil.cpu_percent(interval=1)
    health_stats['CPU Usage'] = f"{cpu_usage:.2f}%"
    if cpu_usage > 80:
        print(f"High CPU usage detected: {cpu_usage:.2f}%.")
        return health_stats

    # Check available memory
    memory = psutil.virtual_memory()
    available_memory_mb = memory.available / (1024 ** 2)  # Convert bytes to MB
    used_memory_mb = memory.used / (1024 ** 2)  # Convert bytes to MB
    total_memory_mb = memory.total / (1024 ** 2)  # Convert bytes to MB
    health_stats['Memory'] = (f"Total: {total_memory_mb:.2f} MB, "
                              f"Used: {used_memory_mb:.2f} MB, "
                              f"Available: {available_memory_mb:.2f} MB")
    if available_memory_mb < 500:  # Minimum 500 MB free memory
        print(f"Low available memory detected: {available_memory_mb:.2f} MB.")
        return health_stats

    # Check disk usage
    disk = psutil.disk_usage('/')
    disk_total_gb = disk.total / (1024 ** 3)  # Convert bytes to GB
    disk_used_gb = disk.used / (1024 ** 3)  # Convert bytes to GB
    disk_free_gb = disk.free / (1024 ** 3)  # Convert bytes to GB
    health_stats['Disk'] = (f"Total: {disk_total_gb:.2f} GB, "
                            f"Used: {disk_used_gb:.2f} GB, "
                            f"Free: {disk_free_gb:.2f} GB, "
                            f"Usage: {disk.percent}%")
    if disk.percent > 90:
        print(f"High disk usage detected: {disk.percent:.2f}%.")
        return health_stats

    # Check network stats
    network = psutil.net_io_counters()
    bytes_sent_mb = network.bytes_sent / (1024 ** 2)  # Convert bytes to MB
    bytes_recv_mb = network.bytes_recv / (1024 ** 2)  # Convert bytes to MB
    health_stats['Network'] = (f"Bytes Sent: {bytes_sent_mb:.2f} MB, "
                               f"Bytes Received: {bytes_recv_mb:.2f} MB")

    print("Health check passed.")
    return health_stats

def generate_random_numbers_with_delay(n, delay, check_interval=5):
    """
    Generates a list of random numbers between 1 and 1000 with a specified delay between generations.
    Performs health checks periodically during the generation process.

    Parameters:
    - n: Number of random numbers to generate
    - delay: Delay in seconds between each number generation
    - check_interval: Interval in numbers at which to perform health checks during the process

    Returns:
    - A list of generated random numbers
    """
    print(f"Generating {n} random numbers with a delay of {delay * 1000:.2f} ms...")

    numbers = []
    start_time = time.time()
    
    for i in range(n):
        number = random.randint(1, 1000)
        numbers.append(number)
        time.sleep(delay)  # Introduce controlled delay (in seconds)

        # Perform health check periodically
        if (i + 1) % check_interval == 0:
            print(f"\nHealth check during generation (after {i + 1} numbers):")
            health_check()

    end_time = time.time()
    total_time = end_time - start_time
    formatted_time = format_time(total_time)
    print(f"Total time taken for generation: {formatted_time}")
    return numbers

def main():
    # Perform health check before generating random numbers
    print("Health check before generating random numbers:")
    pre_check_stats = health_check()
    
    # Exit if health check fails
    if not pre_check_stats:
        print("System health check failed before starting random number generation.")
        return

    # Parameters for random number generation
    n = 10  # Number of random numbers to generate
    delay = 0.050  # Delay in seconds (50 milliseconds)

    # Measure performance before generating random numbers
    print("\nPerformance metrics before generating random numbers:")
    collect_performance_metrics()

    # Generate random numbers with the given delay
    random_numbers = generate_random_numbers_with_delay(n, delay)
    print("Generated numbers:", random_numbers)

    # Measure performance after generating random numbers
    print("\nPerformance metrics after generating random numbers:")
    collect_performance_metrics()

    # Perform health check after generating random numbers
    print("\nHealth check after generating random numbers:")
    post_check_stats = health_check()

    # Print differences
    print("\nHealth Check Results Before and After:")
    print("Before:")
    for key, value in pre_check_stats.items():
        print(f"{key}: {value}")
    
    print("\nAfter:")
    for key, value in post_check_stats.items():
        print(f"{key}: {value}")

if __name__ == "__main__":
    main()
