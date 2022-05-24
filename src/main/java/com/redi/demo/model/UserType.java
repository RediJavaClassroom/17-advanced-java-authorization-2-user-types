package com.redi.demo.model;

public enum UserType {
    FREE(10),
    BASIC(100),
    PREMIUM(Integer.MAX_VALUE);

    private int count;

    UserType(int count){
        this.count = count;
    }

    int getCount() {
        return this.count;
    }
}
