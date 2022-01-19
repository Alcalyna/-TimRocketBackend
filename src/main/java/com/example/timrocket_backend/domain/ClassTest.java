package com.example.timrocket_backend.domain;

public class ClassTest {
    private String firstName;
    private String secondName;

    public ClassTest(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public ClassTest() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }
}
