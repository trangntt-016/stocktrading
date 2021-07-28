package com.canada.edu.stocktrading.model;

public enum OrderSide {
    BUY,
    SELL;

    @Override
    public String toString() {
        switch(this) {
            case BUY: return "side:BUY";
            case SELL: return "side:SELL";
            default: throw new IllegalArgumentException();
        }
    }
}
