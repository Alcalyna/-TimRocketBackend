package com.example.timrocket_backend.api;

import com.example.timrocket_backend.security.SecurityServiceInterface;
import com.example.timrocket_backend.service.TopicService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path ="/topics")
public class TopicController {
    private final TopicService topicService;
    private final Logger logger;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
        this.logger = LoggerFactory.getLogger(TopicController.class);
    }
}
