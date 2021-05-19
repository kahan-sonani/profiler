package com.reb3llion.profiler.utils;

public enum MODE {
    ADD(0), UPDATE(1);

    private int value;
    MODE(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
