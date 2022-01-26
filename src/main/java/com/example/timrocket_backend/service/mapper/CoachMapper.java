package com.example.timrocket_backend.service.mapper;

import com.example.timrocket_backend.domain.User;
import com.example.timrocket_backend.service.dto.CoachDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CoachMapper {

    private final UserMapper userMapper;
    private final CoachInformationMapper coachInformationMapper;
    private final CoachTopicMapper coachTopicMapper;

    @Autowired
    public CoachMapper(UserMapper userMapper, CoachInformationMapper coachInformationMapper, CoachTopicMapper coachTopicMapper) {
        this.userMapper = userMapper;
        this.coachInformationMapper = coachInformationMapper;
        this.coachTopicMapper = coachTopicMapper;
    }

    public CoachDTO mapUserToCoachDto(User user){
        return new CoachDTO(userMapper.userToUserDto(user),
                coachInformationMapper.mapToCoachInformationDTO(user.getCoachInformation()),
                user.getCoachTopics().stream().map(coachTopicMapper::mapToCoachTopicDTO).toList());
    }
}
