import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) throws IOException {
        String serverIp = "localhost";
        int serverPort = 12345;


        DatagramSocket clientSocket = new DatagramSocket();


        String message = "Hello, server!";
        byte[] sendData = message.getBytes();


        InetAddress serverAddress = InetAddress.getByName(serverIp);
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        clientSocket.send(sendPacket);


        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);


        clientSocket.receive(receivePacket);


        String responseMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("Received from server: " + responseMessage);


        clientSocket.close();
    }
}

