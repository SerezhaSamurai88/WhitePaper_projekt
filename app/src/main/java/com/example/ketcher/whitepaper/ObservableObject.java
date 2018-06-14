package com.example.ketcher.whitepaper;

import java.util.Observable;

public class ObservableObject extends Observable {
    private static ObservableObject instance = new ObservableObject();

    public static ObservableObject getInstance() {
        return instance;
    }

    public ObservableObject() {
    }

    public void updateValue() {
        synchronized (this) {
            setChanged();
            notifyObservers();
        }
    }
}
