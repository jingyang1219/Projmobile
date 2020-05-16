package com.example.projectmobile.presentation.model;


import java.util.List;

public class RestDogResponse {
    private List<Dog> messages;
    private String status;

    public List<Dog> getInformation() {
        return messages;
    }

    public String getStatus() {
        return status;
    }
}
