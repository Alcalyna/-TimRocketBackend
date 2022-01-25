package com.example.timrocket_backend.service.mapper;

import com.example.timrocket_backend.domain.User;
import com.example.timrocket_backend.security.SecurityRole;
import com.example.timrocket_backend.service.dto.CreateUserDTO;
import com.example.timrocket_backend.service.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User createUserDtoToUser(CreateUserDTO createUserDTO) {
        User user = new User(createUserDTO.firstName(), createUserDTO.lastName(), createUserDTO.email(), createUserDTO.password(), createUserDTO.company(), SecurityRole.COACHEE);
        return user;
    }

    public UserDTO userToUserDto(User user) {
        UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getCompany(), user.getRole().name(), user.getPictureUrl());
        return userDTO;
    }

}

