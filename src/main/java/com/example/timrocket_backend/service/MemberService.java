package com.example.timrocket_backend.service;

import com.example.timrocket_backend.domain.Member;
import com.example.timrocket_backend.repository.MemberRepository;
import com.example.timrocket_backend.service.dto.CreateMemberDTO;
import com.example.timrocket_backend.service.dto.MemberDTO;
import com.example.timrocket_backend.service.dto.MemberInformationDTO;
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

    public void isLoggedIn(UUID id, UUID id1) {
    }

    public MemberInformationDTO getInformation(Member loggedMember) {
        return null;
    }

}