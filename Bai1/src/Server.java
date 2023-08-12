import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 8888;

        try {
            DatagramSocket serverSocket = new DatagramSocket(port);
            System.out.println("Server is listening on port " + port);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(receivePacket);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receiveData);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            FileInfo fileInfo = (FileInfo) objectInputStream.readObject();

            System.out.println("Receiving file: " + fileInfo.getFileName());

            FileOutputStream fileOutputStream = new FileOutputStream(fileInfo.getFileName());
            byte[] fileData = new byte[fileInfo.getPartSize()];
            DatagramPacket filePacket = new DatagramPacket(fileData, fileData.length);

            for (int i = 0; i < fileInfo.getNumParts(); i++) {
                serverSocket.receive(filePacket);
                fileOutputStream.write(filePacket.getData(), 0, filePacket.getLength());
            }

            fileOutputStream.close();
            objectInputStream.close();
            serverSocket.close();

            System.out.println("File received successfully!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
