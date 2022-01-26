package com.example.timrocket_backend.service.dto;

import com.example.timrocket_backend.domain.CoachTopic;

import java.util.List;

public record CoachDTO(UserDTO user,
                       CoachInformationDTO coachInformation,
                       List<CoachTopicDTO> coachTopics) {
}

