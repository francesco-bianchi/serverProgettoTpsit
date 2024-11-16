package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GestoreServer extends Thread {
    Socket socket;
    Socket socket2;
    ArrayList user;
    Chat chat;
    Contatto c;

    public GestoreServer(Socket socket, Socket socket2, ArrayList<String> user, Chat chat) {
        this.socket = socket;
        this.socket2 = socket2;
        this.user = user;
        this.chat = chat;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            boolean presente = false;
            String listaContatti2 = "";
            String fraseRic = "";
            String username = "";
            String listaContatti = "";
            String[] fraseSplit;
            String[] ricevutaSplit;

            do {
                presente = false;
                username = in.readLine();

                if (user.contains(username)) {
                    out.writeBytes("KOS" + "\n");
                    presente = true;
                } else {
                    user.add(username);
                    c = new Contatto(username);
                    chat.getContatti().add(c); // aggiunto il contatto 
                    listaContatti2 = String.join(",", chat.getContattiUser());
                    System.out.println(listaContatti2);
                    out.writeBytes("OKS" + "\n");
                }

            } while (presente);

            do { // fai switch
                fraseRic = in.readLine();
                fraseSplit = fraseRic.split(":"); // Si divide la stringa per controllare ciò che l'utente passa

                if (fraseSplit[0].equals("C")) { // Invia una lista di contatti

                    if (chat.getContattiUser() == null) {
                        out.writeBytes("" + "\n");
                    } else {
                        listaContatti = String.join(",", chat.getContattiUser());
                        System.out.println(listaContatti);
                        out.writeBytes(listaContatti + "\n");
                    }
                } else if (fraseSplit[0].equals("G")) { // Invia una lista di contatti
                    String listaGruppi = String.join(",", chat.getGruppi());
                    System.out.println(listaGruppi);
                    out.writeBytes(listaGruppi + "\n");
                } else if (fraseSplit[0].equals("M")) {
                    if(chat.getContattiUser().size()==1){
                        out.writeBytes("KKO" + "\n");
                    }
                    else{
                        out.writeBytes("OKK" + "\n");
                        String ricevuta = in.readLine();
                        ricevutaSplit = ricevuta.split(":");
                        if (ricevutaSplit[0].equals("PRIV")) {
                            if(chat.getContattiUser().contains(ricevutaSplit[1])){
                                Contatto dest = new Contatto(ricevutaSplit[1]);
                                System.out.println(dest.getUsername());
                                dest.aggiungiMessaggio(ricevutaSplit[2]); // aggiungo il messaggio all'arrayList del contatto
                                out.writeBytes(ricevutaSplit[1] + ":"+ ricevutaSplit[2]+ "\n");
                            }
                            else
                                out.writeBytes("KKO" + "\n");
                                
                            
                        } else if (ricevutaSplit[0].equals("GRP")) {
                            if (chat.getGruppi().contains(ricevutaSplit[1])) {
                                chat.getGruppoNome(ricevutaSplit[1]).aggiungiMessaggio(ricevutaSplit[2]);
                                out.writeBytes("OK\n");
                            } else {
                                out.writeBytes("KO\n");
                            }
                        } else if (ricevutaSplit[0].equals("ALL")) { // finire di aggiungere il messaggio a tutti i contatti
                            for (int i = 0; i < chat.getContattiUser().size(); i++) {
                                chat.getContattiUser().get(i);
                            }
                        }
                    }
                    
                } else if (fraseSplit[0].equals("CREATE")) {
                    boolean trovatoGruppo = chat.aggiungiGruppo(fraseSplit[1]);
                    if (trovatoGruppo)
                        out.writeBytes("KOG\n");
                    else
                        out.writeBytes("OKG\n");
                } else if (fraseSplit[0].equals("PART")) {
                    Gruppo gNome = chat.getGruppoNome(fraseSplit[1]);
                    boolean trovatoMembro = gNome.aggiungiMembro(chat.getContattoNome(username));

                    if (trovatoMembro)
                        out.writeBytes("KOP\n"); // se trova
                    else
                        out.writeBytes("OKP\n");
                } else if (fraseSplit[0].equals("EXT")) {
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
