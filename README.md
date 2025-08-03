# Java Multithreaded Client-Server with GUI

This project is a simple multithreaded client-server system written in Java. It features:

- Concurrent client handling using threads
- GUI-based client application
- Server-side logging of requests
- Server shutdown via console command
- Client response time measurement

## 🚀 How to Run

### 1. Compile the Project

```bash
javac *.java

java Server
```

It listens on port 8010
Type exit in the console to shut it down

```bash
java ClientGUI
```
Input server IP (localhost for same machine)

Hit Connect to send request and view response time

Multithreading: Each client handled in its own thread

GUI Client: User-friendly client using Swing

Request Logging: All connections logged in requests.log

Graceful Shutdown: Type exit in server console to stop
