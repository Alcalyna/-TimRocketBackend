package com.example.timrocket_backend.service.dto;

public class UserInformationDTO {
    String firstName;
    String lastName;
    String email;
    String company;
    String role;
    String pictureUrl;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCompany() {
        return company;
    }

    public String getRole() {
        return role;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public UserInformationDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserInformationDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserInformationDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserInformationDTO setCompany(String company) {
        this.company = company;
        return this;
    }

    public UserInformationDTO setRole(String role) {
        this.role = role;
        return this;
    }

    public UserInformationDTO setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }
}
