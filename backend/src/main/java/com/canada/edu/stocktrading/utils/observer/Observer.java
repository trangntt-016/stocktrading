package com.canada.edu.stocktrading.utils.observer;


public interface Observer<T> {
    void handle (PropertyChangedEventArgs<T> args);
}