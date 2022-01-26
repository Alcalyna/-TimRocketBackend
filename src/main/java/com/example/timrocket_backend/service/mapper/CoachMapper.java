package com.example.timrocket_backend.service.mapper;

import com.example.timrocket_backend.domain.User;
import com.example.timrocket_backend.service.dto.CoachDTO;
import org.springframework.stereotype.Component;

@Component
public class CoachMapper {

    public CoachDTO mapUserToCoachDto(User user){
        return new CoachDTO(
                user.getFirstName(),
                user.getCoachInformation().getIntroduction()
        );
    }
}
