package com.example.timrocket_backend.api;

import com.example.timrocket_backend.security.SecurityService;
import com.example.timrocket_backend.service.UserService;
import com.example.timrocket_backend.service.dto.CreateUserDTO;
import com.example.timrocket_backend.service.dto.UserDTO;
import com.example.timrocket_backend.service.dto.UserInformationDTO;
import com.example.timrocket_backend.security.SecurityServiceInterface;
import com.example.timrocket_backend.service.MemberService;
import com.example.timrocket_backend.service.dto.CreateMemberDTO;
import com.example.timrocket_backend.service.dto.MemberDTO;
import com.example.timrocket_backend.service.dto.MemberInformationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/members")
public class UserController {

    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String RESET = "\u001B[0m";

    private final UserService userService;
    private final Logger logger;
    private final SecurityServiceInterface securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
        this.logger = LoggerFactory.getLogger(UserController.class);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createMember(@Valid @RequestBody CreateUserDTO createUserDTO){
        UserDTO userDTO = userService.createUser(createUserDTO);
        return userDTO;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getMembers() {
        return userService.getAllUsers();
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

//    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasAnyAuthority('GET_MEMBER_INFORMATION')")
//    public MemberInformationDTO getMemberInformation(@PathVariable UUID id, @RequestBody UserLoggedDTO userLoggedDTO) {
//        System.out.println(ANSI_PURPLE + " Step 1 " + RESET);
//        Member loggedMember = memberService.getMemberByEmail(userLoggedDTO);
//        System.out.println(ANSI_PURPLE + " Step 2 " + RESET);
//        memberService.isLoggedIn(loggedMember.getId(), id);
//        System.out.println(ANSI_PURPLE + " Step 3 " + RESET);
//        MemberInformationDTO memberInfo = memberService.getInformation(loggedMember);
//        System.out.println(ANSI_PURPLE + " Step 4 " + RESET);
//        return memberInfo;
//    }


    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserInformationDTO getMemberByEmail(@PathVariable String email){
        System.out.println(ANSI_PURPLE + "Here!!!!!!!!" + RESET);
        return userService.getByEmail(email);
    }
}

