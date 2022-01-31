package com.example.timrocket_backend.domain;

import com.example.timrocket_backend.domain.topic.CoachTopic;
import com.example.timrocket_backend.security.SecurityRole;
import com.google.common.hash.Hashing;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CODECOACH_USER")
public class User {
    private final static String DEFAULT_PROFILE_PICTURE = "assets/default-profile-picture.jpg";

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private UUID id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name= "LASTNAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private SecurityRole role;

    @Column(name = "PICTURE_URL")
    private String pictureUrl;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private CoachInformation coachInformation;

    @OneToMany()
    @JoinColumn(name = "USER_ID")
    private List<CoachTopic> coachTopics;

    public User(String firstName, String lastName, String email, String password, SecurityRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = Hashing.sha256().hashString(password + "salt", StandardCharsets.UTF_8).toString();
        this.role = role;
        this.pictureUrl = DEFAULT_PROFILE_PICTURE;
    }

    public User() {
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

    public SecurityRole getRole() {
        return role;
    }

    public String getRoleName() {
        String lowercaseName = this.role.name().toLowerCase();
        return lowercaseName.substring(0, 1).toUpperCase() + lowercaseName.substring(1);
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public CoachInformation getCoachInformation() {
        return coachInformation;
    }

    public List<CoachTopic> getCoachTopics() {
        return coachTopics;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setRole(SecurityRole role) {
        this.role = role;
        return this;
    }

    public User setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public User setCoachInformation(CoachInformation coachInformation) {
        this.coachInformation = coachInformation;
        return this;
    }

    public User setCoachTopics(List<CoachTopic> coachTopics) {
        this.coachTopics = coachTopics;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", coachInformation=" + coachInformation +
                ", coachTopics=" + coachTopics +
                '}';
    }
}

