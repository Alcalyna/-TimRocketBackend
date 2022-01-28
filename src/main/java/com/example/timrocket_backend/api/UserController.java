package com.example.timrocket_backend.api;

import com.example.timrocket_backend.security.SecurityServiceInterface;
import com.example.timrocket_backend.service.UserService;
import com.example.timrocket_backend.service.dto.CreateUserDTO;
import com.example.timrocket_backend.service.dto.UpdateUserDTO;
import com.example.timrocket_backend.service.dto.UserDTO;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;
    private final Logger logger;
    private final SecurityServiceInterface securityService;

    public UserController(UserService userService, SecurityServiceInterface securityService) {
        this.userService = userService;
        this.securityService = securityService;
        this.logger = LoggerFactory.getLogger(UserController.class);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@Valid @RequestBody CreateUserDTO createUserDTO){
        UserDTO userDTO = userService.createUser(createUserDTO);
        return userDTO;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            logger.error(errorMessage, exception);
        });
        return errors;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserByEmail(@PathVariable String email){
        return userService.getByEmail(email);
    }

//    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public UserDTO getUserByEmail(@RequestParam String id){
//        return userService.getById(id);
//    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('UPDATE_PROFILE')")
    public UserDTO updateUser(@PathVariable String id, @RequestBody UpdateUserDTO updateUserDTO, Authentication authentication) {
        SimpleKeycloakAccount simpleKeycloakAccount = (SimpleKeycloakAccount)authentication.getDetails();
        AccessToken token = simpleKeycloakAccount.getKeycloakSecurityContext().getToken();
        String loggedInUserEmailAddress = token.getPreferredUsername();
        UserDTO user = userService.getByEmail(loggedInUserEmailAddress);
        return userService.updateUser(id, updateUserDTO, user);
    }



}

