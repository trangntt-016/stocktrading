package com.canada.edu.stocktrading.model;

public enum OrderType {
    LIMIT,
    MARKET;

    @Override
    public String toString() {
        switch(this) {
            case LIMIT: return "type:LIMIT";
            case MARKET: return "type:MARKET";
            default: throw new IllegalArgumentException();
        }
    }
}
