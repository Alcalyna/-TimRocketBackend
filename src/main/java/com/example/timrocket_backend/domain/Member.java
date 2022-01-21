package com.example.timrocket_backend.domain;

import com.example.timrocket_backend.security.SecurityRole;
import com.google.common.hash.Hashing;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Entity
@Table(name = "MEMBERS")
public class Member {
    private final static String DEFAULT_PROFILE_PICTURE = "assets/default-profile-picture.jpg";

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private UUID id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name= "LASTNAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "COMPANY")
    private String company;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private SecurityRole role;

    @Column(name = "PICTURE_URL")
    private String pictureUrl;

    public Member(String firstName, String lastName, String email, String password, String company, SecurityRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = Hashing.sha256().hashString(password + "salt", StandardCharsets.UTF_8).toString();
        this.company = company;
        this.role = role;
        this.pictureUrl = DEFAULT_PROFILE_PICTURE;
    }

    public Member(String firstName, String lastName, String email, String password, String company, SecurityRole role, String pictureUrl) {
        this(firstName, lastName, email, password, company, role);
        this.pictureUrl = pictureUrl;
    }

    public Member() {
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCompany() {
        return company;
    }

    public SecurityRole getRole() {
        return role;
    }

    public String getRoleName() {
        return this.role.name().toLowerCase();
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

}