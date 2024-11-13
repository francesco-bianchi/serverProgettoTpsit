package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class GestoreServer extends Thread{
    Socket socket;
    ArrayList user;
    Chat chat;
    public GestoreServer(Socket socket, ArrayList <String> user) {
        this.socket = socket;
        this.user = user;
    }

    public void run(){
        try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                boolean presente = false;
                String fraseRic="";
                String username ="";
                String[] fraseSplit;
                String[] ricevutaSplit;

                do {
                    presente = false;
                    username = in.readLine();
                    
                    if(user.contains(username)){
                        out.writeBytes("KOS" + "\n");
                        presente = true;
                    }
                    else{
                        user.add(username);
                        out.writeBytes("OKS" +"\n");
                    }
                    
                } while (presente);

                

                do { //fai switch
                    fraseRic = in.readLine();
                    fraseSplit = fraseRic.split(":"); //Si divide la stringa per controllare ciò che l'utente passa
    
                    if(fraseSplit[0].equals("C")){ //Invia una lista di contatti
                        String listaContatti = String.join(",", chat.getContatti());
                        System.out.println(listaContatti);
                        out.writeBytes(listaContatti + "\n");
                    }
                    else if(fraseSplit[0].equals("G")){ //Invia una lista di contatti
                        String listaGruppi = String.join(",", chat.getGruppi());
                        System.out.println(listaGruppi);
                        out.writeBytes(listaGruppi + "\n");
                    }
                    else if(fraseSplit[0].equals("M")){
                        out.writeBytes("OKK");
                        String ricevuta = in.readLine();
                        ricevutaSplit = ricevuta.split(":");
                        if(ricevutaSplit[0].equals("PRIV")){
                            chat.aggiungiContatto(ricevutaSplit[1]); //aggiunto il contatto se non già presente
                            chat.getContattoNome(ricevutaSplit[1]).aggiungiMessaggio(ricevutaSplit[2]); //aggiungo il messaggio all'arrayList del contatto
                            out.writeBytes("OK");
                        }
                        else if(ricevutaSplit[0].equals("GRP")){
                            if(chat.getGruppi().contains(ricevutaSplit[1])){
                                chat.getGruppoNome(ricevutaSplit[1]).aggiungiMessaggio(ricevutaSplit[2]);
                                out.writeBytes("OK");
                            }
                            else{
                                out.writeBytes("KO");
                            }
                        }
                        else if(ricevutaSplit[0].equals("ALL")){ //finire di aggiungere il messaggio a tutti i contatti
                            for(int i=0; i<chat.getContatti().size();i++){
                                chat.getContatti().get(i);
                            }
                        }
                    }
                    else if(fraseSplit[0].equals("CREATE")){
                        boolean trovatoGruppo = chat.aggiungiGruppo(fraseSplit[1]);
                        if(trovatoGruppo)
                            out.writeBytes("KOG");
                        else
                            out.writeBytes("OKG");
                    }
                    else if(fraseSplit[0].equals("PART")){
                        Gruppo gNome = chat.getGruppoNome(fraseSplit[1]);
                        boolean trovatoMembro = gNome.aggiungiMembro(chat.getContattoNome(username));

                        if(trovatoMembro)
                            out.writeBytes("KOP"); //se trova
                        else
                            out.writeBytes("OKP");
                    }
                    else if(fraseSplit[0].equals("EXT")){
                        System.out.println("Client disconnesso");
                        break;
                    }
                    
                } while (!fraseSplit[0].equals("EXT"));



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

