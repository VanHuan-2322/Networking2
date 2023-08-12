import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 8888;

        try {
            File fileToSend = new File("file.txt");
            String fileName = fileToSend.getName();
            long fileSize = fileToSend.length();
            int partSize = 32 * 1024; // 32KB

            int numParts = (int) (fileSize / partSize);
            if (fileSize % partSize != 0) {
                numParts++;
            }

            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverInetAddress = InetAddress.getByName(serverAddress);

            FileInfo fileInfo = new FileInfo(fileName, numParts, partSize, fileSize);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(fileInfo);
            byte[] sendData = byteArrayOutputStream.toByteArray();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverInetAddress, serverPort);
            clientSocket.send(sendPacket);

            FileInputStream fileInputStream = new FileInputStream(fileToSend);
            byte[] fileData = new byte[partSize];
            DatagramPacket filePacket = new DatagramPacket(fileData, fileData.length, serverInetAddress, serverPort);

            int bytesRead;
            for (int i = 0; i < numParts; i++) {
                bytesRead = fileInputStream.read(fileData);
                filePacket.setData(fileData);
                filePacket.setLength(bytesRead);
                clientSocket.send(filePacket);
            }

            fileInputStream.close();
            objectOutputStream.close();
            clientSocket.close();

            System.out.println("File sent successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
