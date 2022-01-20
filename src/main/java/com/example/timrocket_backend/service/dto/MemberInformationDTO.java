package com.example.timrocket_backend.service.dto;

public class MemberInformationDTO {
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

    public MemberInformationDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public MemberInformationDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public MemberInformationDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public MemberInformationDTO setCompany(String company) {
        this.company = company;
        return this;
    }

    public MemberInformationDTO setRole(String role) {
        this.role = role;
        return this;
    }

    public MemberInformationDTO setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }
}
