package com.canada.edu.stocktrading.utils.observer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PropertyChangedEventArgs<T>{
    public T source;
    public String userId;
    public String symbol;
}
