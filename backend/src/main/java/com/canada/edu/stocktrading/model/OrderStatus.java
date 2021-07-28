package com.canada.edu.stocktrading.model;

public enum OrderStatus {
    WORKING,
    FILLED,
    CANCEL;

    @Override
    public String toString() {
        switch(this) {
            case WORKING: return "status:WORKING";
            case FILLED: return "status:FILLED";
            case CANCEL: return "status:CANCEL";
            default: throw new IllegalArgumentException();
        }
    }
}
