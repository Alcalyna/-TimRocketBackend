package com.example.timrocket_backend.service.mapper;

import com.example.timrocket_backend.domain.CoachInformation;
import com.example.timrocket_backend.service.dto.CoachInformationDTO;
import org.springframework.stereotype.Component;

@Component
public class CoachInformationMapper {

    public CoachInformationDTO mapToCoachInformationDTO(CoachInformation coachInformation) {
        return new CoachInformationDTO(coachInformation.getCoachXp(),
                coachInformation.getIntroduction(),
                coachInformation.getAvailability());
    }
}
