package com.example.davide.toiletteapp;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class StatusClass extends Observable{

    protected ArrayList<Observer> observers = new ArrayList<>();
    private String status;

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
        super.addObserver(o);
    }

    @Override
    public void notifyObservers() {
        observers.notify();
        super.notifyObservers();
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        observers.remove(o);
        super.deleteObserver(o);
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {

        this.status = status;
        setChanged();
        notifyObservers(status);

    }
}
