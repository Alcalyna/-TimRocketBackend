package com.example.timrocket_backend.service.mapper;

import com.example.timrocket_backend.domain.Member;
import com.example.timrocket_backend.service.dto.CreateMemberDTO;
import com.example.timrocket_backend.service.dto.MemberDTO;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member createMemberDtoToMember(CreateMemberDTO createMemberDTO) {

        Member member = new Member(createMemberDTO.firstName(), createMemberDTO.lastName(), createMemberDTO.email(), createMemberDTO.password(), createMemberDTO.company(), Member.Role.COACHEE);

        return member;
    }

    public MemberDTO memberToMemberDto(Member member) {

        MemberDTO memberDTO = new MemberDTO(member.getId(), member.getFirstName(), member.getLastName(), member.getEmail(), member.getCompany());

        return memberDTO;
    }
}