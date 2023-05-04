package sg.edu.nus.iss;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

public class Client {

    public static void main(String[] args) throws Exception {
        Socket sock = new Socket("localhost", 3000);

        File file = new File("file.txt");

        OutputStream os = sock.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        InputStream is = sock.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        String status = "";
        while (!status.equals("ok")) {
    
            oos.writeUTF(file.toString());
            oos.flush();
            System.out.println("File name: " + file.toString());

            oos.writeLong(file.length());
            oos.flush();
            System.out.println("File size: " + file.length());

            byte[] fileContent = Files.readAllBytes(file.toPath());
            oos.write(fileContent);
 
            status = ois.readUTF();
            System.out.println(status);
    }

    os.close();
    is.close();
    sock.close();
}

}
