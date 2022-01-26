package com.example.timrocket_backend.service.mapper;

import com.example.timrocket_backend.domain.CoachTopic;
import com.example.timrocket_backend.service.dto.CoachTopicDTO;
import org.springframework.stereotype.Component;

@Component
public class CoachTopicMapper {
    public CoachTopicDTO mapToCoachTopicDTO(CoachTopic coachTopic){
        return new CoachTopicDTO(coachTopic.getTopic().getName(), coachTopic.getExperience());
    }
}
