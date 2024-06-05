package IPC.Code.ThreadSafe;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;



public class Consumer {
    public static void main(String[] args){
        String filename = "ipc_file.txt";
        Path path = Paths.get(filename);

        while(true){
            try(FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)){
                FileLock lock = fileChannel.lock(0, Long.MAX_VALUE,true);

                try{
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int bytesRead = fileChannel.read(buffer);

                    if(bytesRead > 0){
                        buffer.flip();
                        String data = StandardCharsets.UTF_8.decode(buffer).toString();
                        String[] messages = data.split("\n");

                        for(String message : messages){
                            System.out.println("Received: " + message);
                            if(message.equals("Exit")){
                                return;
                            }
                        }
                    }
                }
                finally{
                    lock.release();
                }
                Thread.sleep(1000);
            }
            catch(IOException |InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
