package com.example.bai2;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;

public class ChatGUI extends Application {
    private static final int PORT = 8080;
    private static final String SERVER_ADDRESS = "localhost";
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int clientPort;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        serverAddress = InetAddress.getByName(SERVER_ADDRESS);
        socket = new DatagramSocket();
        clientPort = socket.getLocalPort();

        BorderPane root = new BorderPane();
        TextArea chatArea = new TextArea();
        TextField inputField = new TextField();

        inputField.setOnAction(e -> {
            String message = inputField.getText();
            sendMessage(message);
            inputField.clear();
        });

        root.setCenter(chatArea);
        root.setBottom(inputField);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Chat Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread receiveThread = new Thread(() -> {
            try {
                while (true) {
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    Platform.runLater(() -> chatArea.appendText("Server: " + message + "\n"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        receiveThread.start();
    }

    private void sendMessage(String message) {
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        socket.close();
    }
}

