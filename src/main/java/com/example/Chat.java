package com.example;

import java.util.ArrayList;

public class Chat {
    private ArrayList<Gruppo> gruppi = new ArrayList<>();
    ArrayList <String> arrayGruppi = new ArrayList<>();
    ArrayList<GestoreServer> threads = new ArrayList<GestoreServer>();


    public ArrayList<GestoreServer> getThreads() {
        return threads;
    }

    public void setThreads(ArrayList<GestoreServer> threads) {
        this.threads = threads;
    }

    public boolean aggiungiGruppo(String nomeGruppo) {
        Gruppo g1 = new Gruppo(nomeGruppo);

        if(gruppi.contains(g1)){
            return true;
        }
        this.gruppi.add(g1);
        return false;
    }


    public ArrayList<String> getGruppi() { // ritorna un arrayList di tutti gli username dei gruppi
        for(int i=0; i<gruppi.size();i++){
            arrayGruppi.add(gruppi.get(i).getNome());
        }
        return arrayGruppi;
    }

    public Gruppo getGruppoNome(String nome) { // trova il gruppo a cui si vuole partecipare
        for(int i=0; i<gruppi.size(); i++){
            if(gruppi.get(i).equals(nome)){
                return gruppi.get(i);
            }
        }
        return null;
    }

   

    public void setGruppi(ArrayList<Gruppo> gruppi) {
        this.gruppi = gruppi;
    }

   
    
    
}
