package com.example.timrocket_backend.domain;

import com.google.common.hash.Hashing;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "MEMBER")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
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
    private Role role;

    public Member(String firstName, String lastName, String email, String password, String company, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = Hashing.sha256().hashString(password + "salt", StandardCharsets.UTF_8).toString();
        this.company = company;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public enum Role {
        COACH,
        COACHEE,
        ADMIN
    }
}

