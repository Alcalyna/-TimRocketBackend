package com.example.timrocket_backend.service;

import com.example.timrocket_backend.domain.User;
import com.example.timrocket_backend.exception.AccessProfileException;
import com.example.timrocket_backend.repository.UserRepository;
import com.example.timrocket_backend.security.SecurityUserDTO;
import com.example.timrocket_backend.security.SecurityService;
import com.example.timrocket_backend.service.dto.CreateUserDTO;
import com.example.timrocket_backend.service.dto.UserDTO;
import com.example.timrocket_backend.service.dto.UserInformationDTO;
import com.example.timrocket_backend.service.dto.UserLoggedDTO;
import com.example.timrocket_backend.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    @Context
    SecurityContext securityContext;

    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String RESET = "\u001B[0m";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SecurityService securityService;

    public UserService(UserRepository userRepository, UserMapper userMapper, SecurityService securityService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.securityService = securityService;
    }

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        User user = userMapper.createUserDtoToUser(createUserDTO);
        userRepository.save(user);
        securityService.addUser(new SecurityUserDTO(user.getEmail(), user.getPassword(), user.getRole()));
        UserDTO userDTO = userMapper.userToUserDto(user);
        return userDTO;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(m ->  userMapper.userToUserDto(m)).toList();
    }

    public void isLoggedIn(UUID loggedMemberId, UUID pathId) {
        if(!loggedMemberId.equals(pathId)) {
            throw new AccessProfileException("You are not allowed to check another profile except yours!");
        }
    }

    public UserInformationDTO getInformation(User loggedUser) {
        System.out.println(ANSI_PURPLE + "I am getting the info " + RESET);
        UserInformationDTO userInformationDTO = userMapper.userToUserInformationDto(loggedUser);
        return userInformationDTO;
    }

    public User getUserByEmail(UserLoggedDTO userLoggedDTO) {
        System.out.println(ANSI_PURPLE + "I am getting the member by Email " + RESET);
        User res = userRepository.findByEmail(userLoggedDTO.getEmail());
        System.out.println(ANSI_PURPLE + res.getEmail() + RESET);
        return res;
    }

    public UserInformationDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email);
        UserInformationDTO userInformationDTO = userMapper.userToUserInformationDto(user);
        return userInformationDTO;
    }
}
