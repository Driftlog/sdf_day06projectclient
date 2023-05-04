package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ClientSolution {
    
    public static void main(String[] args) throws Exception{

        Path p = Paths.get(args[0]);
        File f = p.toFile();

        String fileName = f.getName();
        long fileSize = f.length();

        System.out.println();

        Socket sock = new Socket("localhost", 8080);

        try {
            OutputStream os = sock.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);  

            dos.writeUTF(fileName);
            dos.writeLong(fileSize);
            dos.flush();

            byte[] buff = new byte[4 * 1024];
            int size = 0;
            InputStream is = new FileInputStream(f);
            BufferedInputStream bis = new BufferedInputStream(is);

            while ((size = bis.read(buff)) > 0) {
                dos.write(buff, 0, size);
                dos.flush();
                //read from local, write to socket
            }

            dos.close();
            is.close();
        } finally {
            sock.close();
        }

        
    }
}
