package com.example.timrocket_backend.service.mapper;

import com.example.timrocket_backend.domain.topic.Topic;
import com.example.timrocket_backend.service.dto.TopicDTO;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {
    public TopicDTO topicToTopicDto(Topic topic) {
        TopicDTO topicDTO = new TopicDTO(topic.getTopicId(), topic.getName());
        return topicDTO;
    }
}
