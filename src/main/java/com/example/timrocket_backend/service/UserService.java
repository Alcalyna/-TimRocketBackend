package com.example.timrocket_backend.service;

import com.example.timrocket_backend.domain.User;
import com.example.timrocket_backend.repository.UserRepository;
import com.example.timrocket_backend.security.SecurityServiceInterface;
import com.example.timrocket_backend.security.SecurityUserDTO;
import com.example.timrocket_backend.service.dto.CreateUserDTO;
import com.example.timrocket_backend.service.dto.UserDTO;
import com.example.timrocket_backend.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.UUID;


@Service
public class UserService {

    @Context
    SecurityContext securityContext;

    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String RESET = "\u001B[0m";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SecurityServiceInterface securityService;

    public UserService(UserRepository userRepository, UserMapper userMapper, SecurityServiceInterface securityService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.securityService = securityService;
    }

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        User user = userMapper.createUserDtoToUser(createUserDTO);
        userRepository.save(user);
        securityService.addUser(new SecurityUserDTO(user.getEmail(), createUserDTO.password(), user.getRole()));
        UserDTO userDTO = userMapper.userToUserDto(user);
        return userDTO;
    }

    public UserDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email);
        UserDTO userDto= userMapper.userToUserDto(user);
        return userDto;
    }

    public UserDTO getById(UUID id) {
        User user = userRepository.getById(id);
        UserDTO userDTO = userMapper.userToUserDto(user);
        return userDTO;
    }
}
