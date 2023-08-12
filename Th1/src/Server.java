import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        int serverPort = 12345;


        DatagramSocket serverSocket = new DatagramSocket(serverPort);
        System.out.println("Server is running and waiting for client...");


        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);


        serverSocket.receive(receivePacket);


        InetAddress clientAddress = receivePacket.getAddress();
        int clientPort = receivePacket.getPort();


        String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("Received from client: " + message);


        String responseMessage = "Hello, client!";
        byte[] sendData = responseMessage.getBytes();


        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
        serverSocket.send(sendPacket);


        serverSocket.close();
    }
}



