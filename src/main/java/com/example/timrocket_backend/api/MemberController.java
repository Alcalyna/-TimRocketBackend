package com.example.timrocket_backend.api;

import com.example.timrocket_backend.domain.Member;
import com.example.timrocket_backend.security.SecurityService;
import com.example.timrocket_backend.service.MemberService;
import com.example.timrocket_backend.service.dto.CreateMemberDTO;
import com.example.timrocket_backend.service.dto.MemberDTO;
import com.example.timrocket_backend.service.dto.MemberInformationDTO;
import com.example.timrocket_backend.service.dto.UserLoggedDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path = "/members")
public class MemberController {

    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String RESET = "\u001B[0m";

    private final MemberService memberService;
    private final Logger logger;
    private final SecurityService securityService;

    public MemberController(MemberService memberService, SecurityService securityService) {
        this.memberService = memberService;
        this.securityService = securityService;
        this.logger = LoggerFactory.getLogger(MemberController.class);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDTO createMember(@Valid @RequestBody CreateMemberDTO createMemberDTO){
        MemberDTO memberDTO = memberService.createMember(createMemberDTO);
        return memberDTO;
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

    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('GET_MEMBER_INFORMATION')")
    public MemberInformationDTO getMemberInformation(@PathVariable UUID id, @RequestBody UserLoggedDTO userLoggedDTO) {
        System.out.println(ANSI_PURPLE + " Step 1 " + RESET);
        Member loggedMember = memberService.getMemberByEmail(userLoggedDTO);
        System.out.println(ANSI_PURPLE + " Step 2 " + RESET);
        memberService.isLoggedIn(loggedMember.getId(), id);
        System.out.println(ANSI_PURPLE + " Step 3 " + RESET);
        MemberInformationDTO memberInfo = memberService.getInformation(loggedMember);
        System.out.println(ANSI_PURPLE + " Step 4 " + RESET);
        return memberInfo;
    }
}