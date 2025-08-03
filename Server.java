import java.io.*;
import java.net.*;
import java.util.*;
import java.util.function.Consumer;

public class Server {
    private static volatile boolean running = true;
    private static final String LOG_FILE = "requests.log";

    public void stop() {
        running = false;
    }

    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try (
                PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true);
                FileWriter fw = new FileWriter(LOG_FILE, true);
                BufferedWriter logWriter = new BufferedWriter(fw)
            ) {
                String message = "Hello from server " + clientSocket.getInetAddress();
                toSocket.println(message);

                // Log the request
                logWriter.write("Client connected: " + clientSocket.getInetAddress() + " at " + new Date() + "\n");
                logWriter.flush();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(1000); // 1 sec timeout for graceful shutdown check
            System.out.println("Server is listening on port " + port);

            // Shutdown listener
            new Thread(() -> {
                Scanner sc = new Scanner(System.in);
                while (true) {
                    String cmd = sc.nextLine();
                    if (cmd.equalsIgnoreCase("exit")) {
                        running = false;
                        System.out.println("Shutting down server...");
                        break;
                    }
                }
            }).start();

            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                    Thread thread = new Thread(() -> server.getConsumer().accept(clientSocket));
                    thread.start();
                } catch (SocketTimeoutException e) {
                    // Check for shutdown
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}