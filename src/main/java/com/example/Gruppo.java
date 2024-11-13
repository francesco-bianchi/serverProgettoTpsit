package com.example;

import java.util.ArrayList;

public class Gruppo {
    private String nome;
    private ArrayList<Contatto> membri; // Lista dei membri del gruppo
    private ArrayList<String> messaggi; // Lista dei messaggi inviati nel gruppo

    public Gruppo(String nome) {
        this.nome = nome;
        this.membri = new ArrayList<>();
        this.messaggi = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }
    
    public ArrayList<Contatto> getMembri() {
        return membri;
    }

    public ArrayList<String> getMessaggi() {
        return messaggi;
    }

    public boolean aggiungiMembro(Contatto contatto) { //aggiunge un membro a un gruppo
        if(membri.contains(contatto)){
            return true;
        }
        this.membri.add(contatto);
        return false;
    }

    public void aggiungiMessaggio(String messaggio) {
        this.messaggi.add(messaggio);
    }
}
