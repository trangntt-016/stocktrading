package com.canada.edu.stocktrading.utilsGen.observe;

public class PropertyChangedEventArgs<T>{
    public T source;
    public String userId;
    public String symbol;

    PropertyChangedEventArgs(T source, String userId, String symbol) {
        this.source = source;
        this.userId = userId;
        this.symbol = symbol;
    }
}
