package com.example.bai2;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.*;

public class Client {
    private static final int PORT = 8080;
    private static final String SERVER_ADDRESS = "localhost";

    public static void main(String[] args, BufferedReader ConsoleUtils) throws IOException {
        InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
        DatagramSocket socket = new DatagramSocket();

        Thread inThread = new Thread(() -> {
            try {
                while (true) {
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Server: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        inThread.start();

        Thread outThread = new Thread(() -> {
            try {
                while (true) {
                    String message = ConsoleUtils.readLine();
                    byte[] buffer = message.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, PORT);
                    socket.send(packet);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        outThread.start();
    }
}
