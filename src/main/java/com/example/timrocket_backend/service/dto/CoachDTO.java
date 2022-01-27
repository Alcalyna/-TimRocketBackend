package com.example.timrocket_backend.service.dto;

import java.util.List;

public record CoachDTO(UserDTO user,
                       CoachInformationDTO coachInformation,
                       List<CoachTopicDTO> coachTopics) {
}

