# Thread-Safe Inter-Process Communication (IPC) Producer Consumer Example


This is an example of thread-safe Inter-Process Communication (IPC) using Java. The example includes two programs, `Producer` and `Consumer`, which communicate by reading from and writing to a shared file (`ipc_file.txt`). File locking is employed to ensure thread-safe access, preventing race conditions and data corruption.

## Components

### Producer

The `Producer` program simulates a sensor generating random data and writing it to a file. Key points:

- **File Handling**: Opens or creates `ipc_file.txt` for writing.
- **Data Generation**: Generates random sensor data every second.
- **Writing Data**: Writes the sensor data to the file, ensuring exclusive access using file locks.
- **Exit Signal**: Writes an "Exit" message to signal the end of data production.

### Consumer

The `Consumer` program reads and processes data from the file. Key points:

- **File Handling**: Continuously opens `ipc_file.txt` for reading.
- **Reading Data**: Reads data from the file using a shared lock to allow multiple readers.
- **Processing Data**: Prints the received data to the console and checks for the "Exit" message to terminate.

## How to Run

1. **Compile the Java Files**:
   ```sh
   javac Producer.java
   javac Consumer.java

2. Run the Producer Program:
Open a terminal and execute:

    ```sh
    java Producer

3. Run the Consumer Program:
Open another terminal and execute:

    ```sh
    java Consumer

## Explanation
    
- <b>FileChannel</b> : FileChannel is used to handle file I/O operations. It supports reading, writing, and locking files, which is essential for thread-safe IPC.

- <b>FileLock</b> : FileLock ensures that file operations are thread-safe by preventing concurrent access:
    - <b>Exclusive Lock</b>: Used by the Producer to ensure only one instance writes to the file at a time.
    - <b>Shared Lock</b>: Used by the Consumer to allow multiple instances to read from the file simultaneously without interference.

- <b>ByteBuffer</b> : ByteBuffer is used for reading and writing binary data to the file. It wraps the data, making it easier to handle during file operations.

## Locking Mechanism

Both the Producer and Consumer use the locking mechanism provided by FileLock to ensure that:

- <b>Producer</b>: Acquires an exclusive lock before writing data, releases the lock after the write operation.
- <b>Consumer</b>: Acquires a shared lock before reading data, releases the lock after the read operation.

## Data Flow
- The Producer writes random sensor data to the file every second.
- The Consumer reads the data from the file and prints it to the console.
- The process continues until the Producer writes an "Exit" message, signaling the Consumer to terminate.


## How does the code serves as an example of IPC?

- <b>Shared Resource</b>: Both the producer and consumer processes interact with the same file (ipc_file.txt). The producer writes sensor data to this file, and the consumer reads from it. This shared file acts as a medium for communication between the two processes.

- <b>Synchronization</b>: To ensure that the shared resource (the file) is accessed in a thread-safe manner, the code uses file locks. When the producer writes data to the file, it acquires a lock to prevent other processes from writing concurrently. Similarly, the consumer acquires a lock before reading from the file to prevent race conditions and data corruption.

- <b>Communication Protocol</b>: Although the communication in this example is simple (writing sensor data and an "Exit" message), it demonstrates the concept of a communication protocol. The producer and consumer agree on a format for the data exchanged (e.g., sensor data followed by a newline character), enabling the consumer to parse and interpret the information correctly.

- <b> Decoupled Processes</b>: The producer and consumer processes are decoupled, meaning they can run independently and do not need to be aware of each other's internal implementation details. They communicate solely through the shared file, following the agreed-upon communication protocol.

## What type of IPC is demonstrated?

The type of IPC demonstrated in this example is File-based IPC.

File-based IPC involves communication between processes through shared files. In this scenario:

- The producer writes data to a file (ipc_file.txt).
- The consumer reads data from the same file.
- Both processes use file locks to synchronize access to the shared file, ensuring that only one process can write to or read from the file at a time.
- File-based IPC is commonly used when processes need to communicate asynchronously, and a shared file acts as the intermediary for data exchange. It's a straightforward and platform-independent method of IPC, but it may not be the most efficient for real-time or high-speed communication due to file I/O overhead.