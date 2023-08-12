package com.example.bai2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;

public class Server {
    private static final int PORT = 8080;
    private static HashSet<InetAddress> clientAddresses = new HashSet<>();
    private static DatagramSocket socket;

    public static void main(String[] args) throws IOException {
        System.out.println("Server is running...");
        socket = new DatagramSocket(PORT);

        Thread inThread = new Thread(Server::handleIncomingMessages);
        inThread.start();

        Thread outThread = new Thread(Server::handleOutgoingMessages);
        outThread.start();
    }

    private static void handleIncomingMessages() {
        try {
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();
                clientAddresses.add(clientAddress);

                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received: " + message);

                for (InetAddress address : clientAddresses) {
                    if (!address.equals(clientAddress)) {
                        DatagramPacket outgoingPacket = new DatagramPacket(packet.getData(), packet.getLength(), address, clientPort);
                        socket.send(outgoingPacket);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleOutgoingMessages() {
        try {
            while (true) {
                String message = ConsoleUtils.readLine();
                byte[] buffer = message.getBytes();

                for (InetAddress address : clientAddresses) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
                    socket.send(packet);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
