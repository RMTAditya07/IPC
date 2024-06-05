package IPC.Code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Consumer {
    public static void main(String[] args) {
        String fileName = "ipc_file.txt";

        while (true) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Received: " + message);
                    if (message.equals("Exit")) {
                        return;
                    }
                }
                Thread.sleep(1000); // Check for new messages every second
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
