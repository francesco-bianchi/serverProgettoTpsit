package com.example;

import java.util.ArrayList;

public class Contatto {
    String username;
    ArrayList messaggi;

    Contatto(String username) {
        this.username = username;
        this.messaggi = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> getMessaggi() {
        return messaggi;
    }

    public void aggiungiMessaggio(String messaggio) {
        this.messaggi.add(messaggio);
    }
}

