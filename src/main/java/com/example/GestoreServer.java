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
    ArrayList <String> messaggi;
    DataOutputStream out;

    public GestoreServer(Socket socket, ArrayList<String> user, Chat chat) {
        this.socket = socket;
        this.messaggi = new ArrayList<>();
        this.user = user;
        this.chat = chat;
    }
    public ArrayList<String> getMessaggi() {
        return messaggi;
    }
    public void setMessaggi(ArrayList<String> messaggi) {
        this.messaggi = messaggi;
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
                    System.out.println("Utente "+ username + " si è connesso");
                    user.add(username);
                    this.setName(username);
                    chat.getThreads().add(this);
                    out.writeBytes("OKS" + "\n");
                }

            } while (presente);

            do { // fai switch
                String lista;
                String listaMess = "";
                fraseRic = in.readLine();
                fraseSplit = fraseRic.split(":"); // Si divide la stringa per controllare ciò che l'utente passa
                boolean prima = true;
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

                    /*case "G":

                        lista = "";
                        System.out.println("raccolgo nomiG");
                        for (int i = 0; i < chat.getGruppi().size(); i++) {
                            // lista += chat.getGruppi().get(i).getNome() + ";";
                        }

                        System.out.println("invio nomiG");

                        out.writeBytes(lista + "\n");

                        break;*/

                    case "PRIV":
                    boolean inviato = false;
                    if(prima){
                        for (int i = 0; i < chat.getThreads().size(); i++) {
                            if (chat.getThreads().get(i).getName().equals(fraseSplit[1])) {
                                if(fraseSplit.length!=3){
                                    if(!chat.getThreads().get(i).getMessaggi().isEmpty()){
                                        for(int j=0; j<chat.getThreads().get(i).getMessaggi().size(); j++){
                                            listaMess += chat.getThreads().get(i).getMessaggi().get(j);
                                        }
                                        out.writeBytes("l:"+listaMess);
                                        inviato = true;
                                    }
                                    else{
                                        inviato = true;
                                        out.writeBytes("NO\n");
                                    }
                                    
                                }
                                else{
                                    chat.getThreads().get(i).getMessaggi().add(fraseSplit[2]);
                                    /*for (int k = 0; k < chat.getThreads().size(); k++) {
                                        if (chat.getThreads().get(k).getName().equals(this.currentThread().getName())) { // da finire
                                            chat.getThreads().get(0).getMessaggi().add(fraseSplit[2]);
                                        }
                                    }*/
                                    chat.getThreads().get(i).inviaClient(this.getName() + ": " + fraseSplit[2]);
                                    inviato = true;
                                }
                                
                            }
                        }
                        if (!inviato) {
                            out.writeBytes("KO\n");
                        }
                    }
                    else{
                        prima = false;
                        String mess = in.readLine();
                        System.out.println(mess);
                        String[] messSplit = mess.split(":");

                        for (int i = 0; i < chat.getThreads().size(); i++) {
                            if (chat.getThreads().get(i).getName().equals(fraseSplit[1])) {
                                if(fraseSplit.length!=3){
                                    if(!chat.getThreads().get(i).getMessaggi().isEmpty()){
                                        for(int j=0; j<chat.getThreads().get(i).getMessaggi().size(); j++){
                                            listaMess += chat.getThreads().get(i).getMessaggi().get(j);
                                        }
                                        out.writeBytes("l:"+listaMess);
                                        inviato = true;
                                    }
                                    else{
                                        inviato = true;
                                        out.writeBytes("NO\n");
                                    }
                                    
                                }
                                else{
                                    chat.getThreads().get(i).getMessaggi().add(fraseSplit[2]);
                                    chat.getThreads().get(i).inviaClient(this.getName() + ": " + fraseSplit[2]);
                                    inviato = true;
                                }
                                
                            }
                        }
                        
                        if (!inviato) {
                            out.writeBytes("KO\n");
                        }
                    }
                       
                        break;
                    case "ALL":
                        for (int i = 0; i < chat.getThreads().size(); i++) {
                            out.writeBytes("ALL:" + fraseSplit[1] + "\n");
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
