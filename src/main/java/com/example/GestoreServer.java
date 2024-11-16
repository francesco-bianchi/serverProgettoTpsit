package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class GestoreServer extends Thread {
    Socket socket;
    Socket socket2;
    ArrayList<String> user;
    Chat chat;
    BufferedReader in;
    DataOutputStream out;

    public GestoreServer(Socket socket, /* Socket socket2, */ ArrayList<String> user, Chat chat) {
        this.socket = socket;
        // this.socket2 = socket2;
        this.user = user;
        this.chat = chat;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());

            boolean presente = false;
            String fraseRic = "";
            String username = "";
            String[] fraseSplit;

            do {
                presente = false;
                username = in.readLine();

                if (user.contains(username)) {
                    out.writeBytes("KOS" + "\n");
                    presente = true;
                } else {
                    user.add(username);
                    this.setName(username);
                    chat.getThreads().add(this);
                    out.writeBytes("OKS" + "\n");
                }

            } while (presente);

            do { // fai switch
                String lista;
                fraseRic = in.readLine();
                System.out.println(fraseRic);
                fraseSplit = fraseRic.split(":"); // Si divide la stringa per controllare ciò che l'utente passa

                switch (fraseSplit[0]) {
                    case "C":

                        lista = "";
                        System.out.println("raccolgo nomi");
                        for (int i = 0; i < chat.getThreads().size(); i++) {
                            lista += chat.getThreads().get(i).getName() + ";";
                        }

                        System.out.println("invio nomi");

                        out.writeBytes(lista + "\n");

                        break;

                    case "G":

                        lista = "";
                        System.out.println("raccolgo nomiG");
                        for (int i = 0; i < chat.getGruppi().size(); i++) {
                            // lista += chat.getGruppi().get(i).getNome() + ";";
                        }

                        System.out.println("invio nomiG");

                        out.writeBytes(lista + "\n");

                        break;

                    case "PRIV":
                        String mess = in.readLine();
                        String[] messSplit = mess.split(":");
                        boolean inviato = false;

                        for (int i = 0; i < chat.getThreads().size(); i++) {
                            if (chat.getThreads().get(i).getName().equals(messSplit[1])) {
                                chat.getThreads().get(i).inviaClient(this.getName() + ": " + messSplit[2]);
                                inviato = true;
                            }
                        }
                        if (!inviato) {
                            out.writeBytes("NONE\n");
                        }
                        break;

                    default:
                        break;
                }

            } while (!fraseSplit[0].equals("EXT"));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void inviaClient(String msg) {

        try {
            out.writeBytes(msg + "\n");

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
