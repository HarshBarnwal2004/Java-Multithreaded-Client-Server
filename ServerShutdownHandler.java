import java.util.Scanner;

public class ServerShutdownHandler implements Runnable {
    private final Server server;

    public ServerShutdownHandler(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input.trim())) {
                System.out.println("Shutting down server...");
                server.stop();
                break;
            }
        }
        scanner.close();
    }
}
