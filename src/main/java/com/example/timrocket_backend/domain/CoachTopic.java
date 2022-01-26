package com.example.timrocket_backend.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "COACH_TOPIC")
public class CoachTopic {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "TOPIC_ID")
    private Topic topic;

    @Column(name= "EXPERIENCE")
    private int experience;

    public UUID getId() {
        return userId;
    }

    public Topic getTopic() {
        return topic;
    }

    public int getExperience() {
        return experience;
    }
}
