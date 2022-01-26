package com.example.timrocket_backend.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "COACH_TOPIC")
public class CoachTopic {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private UUID id;

    @Column(name = "TOPIC_ID")
    private UUID topicId;

    @Column(name= "EXPERIENCE")
    private int introduction;
}
