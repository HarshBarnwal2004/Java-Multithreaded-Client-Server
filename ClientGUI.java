import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.*;

public class ClientGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Client");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JButton connectButton = new JButton("Connect to Server");

        connectButton.addActionListener((ActionEvent e) -> {
            int port = 8010;
            try {
                long start = System.nanoTime();
                Socket socket = new Socket("localhost", port);

                PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                toSocket.println("Hello from GUI Client " + socket.getLocalSocketAddress());
                String response = fromSocket.readLine();
                long end = System.nanoTime();

                outputArea.append(response + "\n");
                outputArea.append("Response Time: " + (end - start) / 1_000_000 + " ms\n");

                socket.close();
            } catch (IOException ex) {
                outputArea.append("Error: " + ex.getMessage() + "\n");
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        frame.add(connectButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
