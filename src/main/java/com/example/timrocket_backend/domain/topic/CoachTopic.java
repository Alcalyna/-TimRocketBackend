package com.example.timrocket_backend.domain.topic;

import javax.persistence.*;
import java.util.UUID;

@Entity
@IdClass(CoachTopicId.class)
@Table(name = "COACH_TOPIC")
public class CoachTopic {
    @Id
    @Column(name = "USER_ID")
    private UUID userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "TOPIC_ID")
    private Topic topic;

    @Enumerated(EnumType.STRING)
    @Column(name= "EXPERIENCE")
    private TopicExperience experience;

    public UUID getId() {
        return userId;
    }

    public Topic getTopic() {
        return topic;
    }

    public TopicExperience getExperience() {
        return experience;
    }
}
