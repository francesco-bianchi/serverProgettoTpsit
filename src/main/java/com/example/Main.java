package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        
        ServerSocket serverSocket = new ServerSocket(3000);
        ServerSocket serverSocket2 = new ServerSocket(4000);
        ArrayList arrUser = new ArrayList<String>();
        Chat chat = new Chat();
        System.out.println("Server partito");
        while(true){
            Socket socket = serverSocket.accept();
            Socket socket2 = serverSocket2.accept();
            GestoreServer gs = new GestoreServer(socket, socket2, arrUser, chat);
            gs.start();
        }
    }
}