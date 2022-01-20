package com.example.timrocket_backend.service;

import com.example.timrocket_backend.domain.Member;
import com.example.timrocket_backend.exception.AccessProfileException;
import com.example.timrocket_backend.repository.MemberRepository;
import com.example.timrocket_backend.service.dto.CreateMemberDTO;
import com.example.timrocket_backend.service.dto.MemberDTO;
import com.example.timrocket_backend.service.dto.MemberInformationDTO;
import com.example.timrocket_backend.service.dto.UserLoggedDTO;
import com.example.timrocket_backend.service.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.UUID;

@Service
public class MemberService {

    @Context
    SecurityContext securityContext;

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String RESET = "\u001B[0m";


    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    public MemberDTO createMember(CreateMemberDTO createMemberDTO) {
        Member member = memberMapper.createMemberDtoToMember(createMemberDTO);
        memberRepository.save(member);
        MemberDTO memberDTO = memberMapper.memberToMemberDto(member);
        return memberDTO;
    }

    public void isLoggedIn(UUID loggedMemberId, UUID pathId) {
        if(!loggedMemberId.equals(pathId)) {
            throw new AccessProfileException("You are not allowed to check another profile except yours!");
        }
    }

    public MemberInformationDTO getInformation(Member loggedMember) {
        System.out.println(ANSI_PURPLE + "I am getting the info " + RESET);
        MemberInformationDTO memberInformationDTO = memberMapper.memberToMemberInformationDto(loggedMember);
        return memberInformationDTO;
    }

    public Member getMemberByEmail(UserLoggedDTO userLoggedDTO) {
        System.out.println(ANSI_PURPLE + "I am getting the member by Email " + RESET);
        Member res = memberRepository.findByEmail(userLoggedDTO.getEmail());
        System.out.println(ANSI_PURPLE + res.getEmail() + RESET);
        return res;
    }

    public MemberInformationDTO getByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        MemberInformationDTO memberInformationDTO = memberMapper.memberToMemberInformationDto(member);
        return memberInformationDTO;
    }
}
