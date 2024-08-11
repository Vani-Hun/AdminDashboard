package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        // Thread for reading messages from the server
        new Thread(new Runnable() {
            public void run() {
                try {
                    String message;
                    while ((message = input.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading from server: " + e);
                }
            }
        }).start();

        // Main thread for sending messages to the server
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        while ((userInput = consoleInput.readLine()) != null) {
            output.println(userInput);
        }

        socket.close();
    }
}

