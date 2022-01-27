package com.example.timrocket_backend.service;

import com.example.timrocket_backend.domain.User;
import com.example.timrocket_backend.exception.EditNotAllowedException;
import com.example.timrocket_backend.repository.UserRepository;
import com.example.timrocket_backend.security.SecurityRole;
import com.example.timrocket_backend.security.SecurityServiceInterface;
import com.example.timrocket_backend.security.SecurityUserDTO;
import com.example.timrocket_backend.service.dto.CreateUserDTO;
import com.example.timrocket_backend.service.dto.UpdateUserDTO;
import com.example.timrocket_backend.service.dto.UserDTO;
import com.example.timrocket_backend.service.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.UUID;


@Service
@Transactional
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
        securityService.addUser(new SecurityUserDTO(user.getEmail(), user.getPassword(), user.getRole()));
        UserDTO userDTO = userMapper.userToUserDto(user);
        return userDTO;
    }

    public UserDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email);
        UserDTO userDto = userMapper.userToUserDto(user);
        return userDto;
    }


    public UserDTO updateUser(String id, UpdateUserDTO updateUserDTO, UserDTO loggedInUser) {
        validateCanEdit(id, loggedInUser);
        User userToUpdate = userRepository.getById(UUID.fromString(id));

        userToUpdate.setFirstName(updateUserDTO.firstName())
                .setLastName(updateUserDTO.lastName())
                .setEmail(updateUserDTO.email())
                .setCompany(updateUserDTO.company())
                .setRole(SecurityRole.getByName(updateUserDTO.role()));


//        userRepository.save(userToUpdate); --> @Transactional is magic

        return userMapper.userToUserDto(userToUpdate);
    }

    private void validateCanEdit(String id, UserDTO loggedInUser) {
        if (!(SecurityRole.getByName(loggedInUser.role()) == (SecurityRole.ADMIN) || id.equals(loggedInUser.id().toString()))) {
            throw new EditNotAllowedException();
        }
//        if(SecurityRole.getByName(loggedInUser.role()) != (SecurityRole.ADMIN)) {
//            throw new EditNotAllowedException();
//        }
    }
}
