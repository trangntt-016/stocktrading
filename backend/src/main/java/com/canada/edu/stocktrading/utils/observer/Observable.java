package com.canada.edu.stocktrading.utils.observer;

import java.util.ArrayList;
import java.util.List;


public class Observable<T>{

    public List<Observer<T>> observers = new ArrayList<>();

    public void subscribe(Observer<T> observer) {
        this.observers.add(observer);
    }

    protected void notifyObservers(T source, String userId, String symbol) {
        for (Observer<T> o : observers) {
            o.handle(new PropertyChangedEventArgs<T>(source, userId, symbol));
        }
    }
}
