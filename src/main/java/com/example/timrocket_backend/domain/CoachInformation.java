package com.example.timrocket_backend.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "COACH_INFORMATION")
public class CoachInformation {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private UUID id;

    @Column(name = "COACH_XP")
    private int coachXp;

    @Column(name= "INTRODUCTION")
    private String introduction;

    @Column(name = "AVAILABILITY")
    private String availability;

    public UUID getId() {
        return id;
    }

    public int getCoachXp() {
        return coachXp;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getAvailability() {
        return availability;
    }
}
