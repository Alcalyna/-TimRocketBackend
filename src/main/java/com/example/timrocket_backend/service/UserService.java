package com.example.timrocket_backend.service;

import com.example.timrocket_backend.domain.User;
import com.example.timrocket_backend.exception.EditNotAllowedException;
import com.example.timrocket_backend.repository.UserRepository;
import com.example.timrocket_backend.security.SecurityRole;
import com.example.timrocket_backend.security.SecurityServiceInterface;
import com.example.timrocket_backend.security.SecurityUserDTO;
import com.example.timrocket_backend.service.dto.CoachDTO;
import com.example.timrocket_backend.service.dto.CreateUserDTO;
import com.example.timrocket_backend.service.dto.UpdateUserDTO;
import com.example.timrocket_backend.service.dto.UserDTO;
import com.example.timrocket_backend.service.mapper.CoachMapper;
import com.example.timrocket_backend.service.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.*;
import java.util.stream.Collectors;
import java.util.UUID;


@Service
@Transactional
public class UserService {

    @Context
    SecurityContext securityContext;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CoachMapper coachMapper;
    private final SecurityServiceInterface securityService;

    public UserService(UserRepository userRepository, UserMapper userMapper, CoachMapper coachMapper, SecurityServiceInterface securityService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.coachMapper = coachMapper;
        this.securityService = securityService;
    }

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        User user = userMapper.createUserDtoToUser(createUserDTO);
        System.out.println(user);
//        userRepository.save(user);
        System.out.println("We are here " + userRepository.save(user));
        userRepository.save(user);
        securityService.addUser(new SecurityUserDTO(user.getEmail(), createUserDTO.password(), user.getRole()));
        UserDTO userDTO = userMapper.userToUserDto(user);
        System.out.println(userDTO);
        return userDTO;
    }

    public UserDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email);
        UserDTO userDto = userMapper.userToUserDto(user);
        return userDto;
    }

    public List<CoachDTO> getAllCoaches() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == SecurityRole.COACH)
                .map(user -> coachMapper.mapUserToCoachDto(user))
                .collect(Collectors.toList());
    }

    public UserDTO getById(UUID id) {
        User user = userRepository.getById(id);
        UserDTO userDTO = userMapper.userToUserDto(user);
        return userDTO;
    }

    public CoachDTO getCoachBy(UUID id) {
        User user = userRepository.getById(id);
        CoachDTO coachDTO = coachMapper.mapUserToCoachDto(user);
        return coachDTO;
    }

    public UserDTO updateUser(String id, UpdateUserDTO updateUserDTO, UserDTO loggedInUser){
            validateCanEdit(id, loggedInUser);
            User userToUpdate = userRepository.getById(UUID.fromString(id));

            userToUpdate.setFirstName(updateUserDTO.firstName())
                    .setLastName(updateUserDTO.lastName())
                    .setEmail(updateUserDTO.email())
                    .setCompany(updateUserDTO.company())
                    .setRole(SecurityRole.getByName(updateUserDTO.role()));

            return userMapper.userToUserDto(userToUpdate);

    }

    private void validateCanEdit(String id, UserDTO loggedInUser) {
        if (!(SecurityRole.getByName(loggedInUser.role()) == (SecurityRole.ADMIN) || id.equals(loggedInUser.userId().toString()))) {
            throw new EditNotAllowedException();
        }
    }
}
