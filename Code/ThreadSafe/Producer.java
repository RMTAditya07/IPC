package IPC.Code.ThreadSafe;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;
import java.nio.ByteBuffer;

public class Producer {
    public static void main(String[] args){
        String filename = "ipc_file.txt";
        Random random = new Random();
        Path path = Paths.get(filename);

        try(FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE)){
            for(int i=0;i < 10; i++){
                int sensorData = random.nextInt(100);
                String data = "Sensor data : "+sensorData+"\n";
                FileLock lock = fileChannel.lock();
                try{
                    ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
                    fileChannel.write(buffer);
                    fileChannel.force(true);
                }
                finally{
                    lock.release();
                }
                Thread.sleep(1000); 
            }
            String exitMessage = "Exit\n";

            FileLock lock = fileChannel.lock();
            try{
                ByteBuffer buffer = ByteBuffer.wrap(exitMessage.getBytes());
                fileChannel.write(buffer);
                fileChannel.force(true);
            }
            finally{
                lock.release();
            }
        }
        catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
