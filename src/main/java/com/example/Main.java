package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        
        ServerSocket serverSocket = new ServerSocket(3000);
        ArrayList arrUser = new ArrayList<String>();
        System.out.println("Server partito");
        while(true){
            Socket socket = serverSocket.accept();
            GestoreServer gs = new GestoreServer(socket , arrUser);
            gs.start();
        }
    }
}