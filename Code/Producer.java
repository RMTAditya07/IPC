package IPC.Code;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Producer {
    public static void main(String[] args) {
        String fileName = "ipc_file.txt";
        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < 10; i++) {
                int sensorData = random.nextInt(100);  // Simulate sensor data
                writer.write("Sensor data: " + sensorData);
                writer.newLine();
                writer.flush();
                Thread.sleep(1000);  // Simulate delay between data points
            }
            writer.write("exit");
            writer.newLine();
            writer.flush();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
