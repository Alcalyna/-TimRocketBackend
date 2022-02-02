package com.example.timrocket_backend.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "COACH_INFORMATION")
public class CoachInformation {
    @Id
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

    public CoachInformation(UUID id, int coachXp, String introduction, String availability) {
        this.id = id;
        this.coachXp = coachXp;
        this.introduction = introduction;
        this.availability = availability;
    }

    public CoachInformation() {
    }

    @Override
    public String toString() {
        return "CoachInformation{" +
                "id=" + id +
                ", coachXp=" + coachXp +
                ", introduction='" + introduction + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }

    public CoachInformation setId(UUID id) {
        this.id = id;
        return this;
    }

    public CoachInformation setCoachXp(int coachXp) {
        this.coachXp = coachXp;
        return this;
    }

    public CoachInformation setIntroduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public CoachInformation setAvailability(String availability) {
        this.availability = availability;
        return this;
    }
}
