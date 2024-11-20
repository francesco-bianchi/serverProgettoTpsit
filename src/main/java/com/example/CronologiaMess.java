package com.example;

import java.util.ArrayList;

public class CronologiaMess {
    
    private ArrayList<String> messaggi; // Lista dei messaggi
    public CronologiaMess(){
        this.messaggi = new ArrayList<>();
    }

    public ArrayList<String> getMessaggi() {
        return messaggi;
    }
    public void setMessaggi(ArrayList<String> messaggi) {
        this.messaggi = messaggi;
    }

    
    
}
