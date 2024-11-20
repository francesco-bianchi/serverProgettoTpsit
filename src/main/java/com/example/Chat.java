package com.example;

import java.util.ArrayList;

public class Chat {
    ArrayList<GestoreServer> threads;

    public Chat(){
        threads = new ArrayList<GestoreServer>();
    }

    public ArrayList<GestoreServer> getThreads() {
        return threads;
    }

    public void setThreads(ArrayList<GestoreServer> threads) {
        this.threads = threads;
    }

   
   
    
    
}
