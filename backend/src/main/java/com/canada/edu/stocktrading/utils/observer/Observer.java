package com.canada.edu.stocktrading.utilsGen.observe;


public interface Observer<T> {
    void handle (PropertyChangedEventArgs<T> args);
}