package com.example.timrocket_backend.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class CoachTopicId implements Serializable {
    private UUID userId;

    private Topic topic;

    public CoachTopicId() {
    }

    public CoachTopicId(UUID userId, Topic topic) {
        this.userId = userId;
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoachTopicId that = (CoachTopicId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(topic, that.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, topic);
    }
}
