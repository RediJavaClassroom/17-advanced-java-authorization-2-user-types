package com.redi.demo.model;

public class UserRegistration {
    public final String email;
    public final String password;
    public final String name;
    public final UserType userType;

    public UserRegistration(
            final String email,
            final String password,
            final String name,
            final UserType userType
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userType = userType;
    }
}
