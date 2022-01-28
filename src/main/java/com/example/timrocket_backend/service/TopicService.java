package com.example.timrocket_backend.service;

import com.example.timrocket_backend.repository.TopicRepository;
import com.example.timrocket_backend.service.mapper.TopicMapper;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicService(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }
}
