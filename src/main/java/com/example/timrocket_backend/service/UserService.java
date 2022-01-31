package com.example.timrocket_backend.service;

import com.example.timrocket_backend.domain.CoachInformation;
import com.example.timrocket_backend.domain.User;
import com.example.timrocket_backend.exception.EditNotAllowedException;
import com.example.timrocket_backend.repository.CoachInformationRepository;
import com.example.timrocket_backend.repository.UserRepository;
import com.example.timrocket_backend.security.SecurityRole;
import com.example.timrocket_backend.security.SecurityService;
import com.example.timrocket_backend.security.SecurityServiceInterface;
import com.example.timrocket_backend.security.SecurityUserDTO;
import com.example.timrocket_backend.service.dto.CoachDTO;
import com.example.timrocket_backend.service.dto.CreateUserDTO;
import com.example.timrocket_backend.service.dto.UpdateUserDTO;
import com.example.timrocket_backend.service.dto.UserDTO;
import com.example.timrocket_backend.service.mapper.CoachMapper;
import com.example.timrocket_backend.service.mapper.UserMapper;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
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
    private final CoachInformationRepository coachInformationRepository;
    private final UserMapper userMapper;
    private final CoachMapper coachMapper;
    private final SecurityServiceInterface securityServiceInterface;

    public UserService(UserRepository userRepository, CoachInformationRepository coachInformationRepository, UserMapper userMapper, CoachMapper coachMapper, SecurityServiceInterface securityServiceInterface) {
        this.userRepository = userRepository;
        this.coachInformationRepository = coachInformationRepository;
        this.userMapper = userMapper;
        this.coachMapper = coachMapper;
        this.securityServiceInterface = securityServiceInterface;
    }

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        User user = userMapper.createUserDtoToUser(createUserDTO);
        userRepository.save(user);
        securityServiceInterface.addUser(new SecurityUserDTO(user.getEmail(), createUserDTO.password(), user.getRole()));
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

    public UserDTO updateUser(String id, UpdateUserDTO updateUserDTO, Authentication authentication) {
        UserDTO loggedInUser = getUserDtoFromAuthentification(authentication);
        validateCanEdit(id, loggedInUser);
        User userToUpdate = userRepository.getById(UUID.fromString(id));

        updateRoleInKeycloak(authentication, updateUserDTO.role());

        userToUpdate.setFirstName(updateUserDTO.firstName())
                .setLastName(updateUserDTO.lastName())
                .setEmail(updateUserDTO.email())
                .setRole(SecurityRole.getByName(updateUserDTO.role()));

        if (userToUpdate.getRoleName().equalsIgnoreCase("coach")) {
            CoachInformation defaultCoachInformation = new CoachInformation(userToUpdate.getId(), 0, "Hey I am new coach!", "");
            coachInformationRepository.save(defaultCoachInformation);

        } else if (userToUpdate.getRoleName().equalsIgnoreCase("coachee")) {
            coachInformationRepository.deleteById(userToUpdate.getId());
        }

        return userMapper.userToUserDto(userToUpdate);
    }

    private void updateRoleInKeycloak(Authentication authentication, String role) {
        SimpleKeycloakAccount simpleKeycloakAccount = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = simpleKeycloakAccount.getKeycloakSecurityContext().getToken();
        String id = token.getSubject();
//        SecurityRole[] roles = SecurityRole.values();
        securityServiceInterface.addRole(securityServiceInterface.getUser(id), role);
        securityServiceInterface.removeRole(securityServiceInterface.getUser(id), "admin");
    }

    private void validateCanEdit(String id, UserDTO loggedInUser) {
        if (!(SecurityRole.getByName(loggedInUser.role()) == (SecurityRole.ADMIN) || id.equals(loggedInUser.userId().toString()))) {
            throw new EditNotAllowedException();
        }
    }

    private UserDTO getUserDtoFromAuthentification(Authentication authentication) {
        SimpleKeycloakAccount simpleKeycloakAccount = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = simpleKeycloakAccount.getKeycloakSecurityContext().getToken();
        String loggedInUserEmailAddress = token.getPreferredUsername();
        return getByEmail(loggedInUserEmailAddress);
    }

    public UserDTO updateRoleToCoach(Authentication authentication) {
        UserDTO loggedInUser = getUserDtoFromAuthentification(authentication);
        User userToUpdate = userRepository.getById(loggedInUser.userId());
        userToUpdate.setRole(SecurityRole.COACH);
        updateRoleInKeycloak(authentication, "coach");
        CoachInformation defaultCoachInformation = new CoachInformation(userToUpdate.getId(), 0, "Hey I am new coach!", "");
        coachInformationRepository.save(defaultCoachInformation);
        return userMapper.userToUserDto(userToUpdate);
    }
}
