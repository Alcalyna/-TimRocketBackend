package com.example.timrocket_backend.service.mapper;

import com.example.timrocket_backend.domain.Member;
import com.example.timrocket_backend.security.SecurityRole;
import com.example.timrocket_backend.service.dto.CreateMemberDTO;
import com.example.timrocket_backend.service.dto.MemberDTO;
import com.example.timrocket_backend.service.dto.MemberInformationDTO;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member createMemberDtoToMember(CreateMemberDTO createMemberDTO) {
        Member member = new Member(createMemberDTO.firstName(), createMemberDTO.lastName(), createMemberDTO.email(), createMemberDTO.password(), createMemberDTO.company(), SecurityRole.COACHEE);
        return member;
    }

    public MemberDTO memberToMemberDto(Member member) {
        MemberDTO memberDTO = new MemberDTO(member.getId(), member.getFirstName(), member.getLastName(), member.getEmail(), member.getCompany());
        return memberDTO;
    }

    public MemberInformationDTO memberToMemberInformationDto(Member member) {
        return new MemberInformationDTO().setFirstName(member.getFirstName())
                .setLastName(member.getLastName())
                .setEmail(member.getEmail())
                .setCompany(member.getCompany())
                .setRole(member.getRoleName())
                .setPictureUrl(member.getPictureUrl());
    }
}

