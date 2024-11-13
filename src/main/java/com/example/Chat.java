package com.example;

import java.util.ArrayList;

public class Chat {
    private ArrayList<Gruppo> gruppi;
    private ArrayList<Contatto> contatti;
    ArrayList <String> user;
    ArrayList <String> arrayGruppi;


    public boolean aggiungiGruppo(String nomeGruppo) {
        Gruppo g1 = new Gruppo(nomeGruppo);

        if(gruppi.contains(g1)){
            return true;
        }
        this.gruppi.add(g1);
        return false;
    }

    public boolean aggiungiContatto(String nomeContatto) {
        Contatto c1 = new Contatto(nomeContatto);
        if(gruppi.contains(c1)){
            return true;
        }
        this.contatti.add(c1);
        return false;
    }

    public ArrayList<String> getGruppi() { // ritorna un arrayList di tutti i gruppi
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

    public Contatto getContattoNome(String nome) { // trova il contatto che deve essere inserito nel gruppo
        for(int i=0; i<contatti.size(); i++){
            if(contatti.get(i).equals(nome)){
                return contatti.get(i);
            }
        }
        return null;
    }

    public void setGruppi(ArrayList<Gruppo> gruppi) {
        this.gruppi = gruppi;
    }

    public ArrayList<String> getContatti() { //ritorna un arrayList di tutti i contatti
        for(int i=0; i<contatti.size();i++){
            user.add(contatti.get(i).getUsername());
        }
        return user;
    }

    public void setContatti(ArrayList<Contatto> contatti) {
        this.contatti = contatti;
    }

    
    
}
