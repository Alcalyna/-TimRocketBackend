package com.example.timrocket_backend.api;

import com.example.timrocket_backend.service.MemberService;
import com.example.timrocket_backend.service.dto.CreateMemberDTO;
import com.example.timrocket_backend.service.dto.MemberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path = "/members")
public class MemberController {

    private final MemberService memberService;
    private final Logger logger;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        this.logger = LoggerFactory.getLogger(MemberController.class);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public MemberDTO createMember(@RequestBody CreateMemberDTO createMemberDTO){

        MemberDTO memberDTO = memberService.createMember(createMemberDTO);

        return memberDTO;
    }
}
