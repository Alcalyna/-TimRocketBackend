package com.example.timrocket_backend.service;

import com.example.timrocket_backend.domain.Member;
import com.example.timrocket_backend.repository.MemberRepository;
import com.example.timrocket_backend.security.Role;
import com.example.timrocket_backend.security.SecurityMemberDTO;
import com.example.timrocket_backend.security.SecurityService;
import com.example.timrocket_backend.service.dto.CreateMemberDTO;
import com.example.timrocket_backend.service.dto.MemberDTO;
import com.example.timrocket_backend.service.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final SecurityService securityService;

    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper, SecurityService securityService) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
        this.securityService = securityService;
    }

    public MemberDTO createMember(CreateMemberDTO createMemberDTO) {
        Member member = memberMapper.createMemberDtoToMember(createMemberDTO);
        memberRepository.save(member);
        securityService.addMember(new SecurityMemberDTO(member.getEmail(), member.getPassword(), Role.valueOf(member.getRole().name())));
        MemberDTO memberDTO = memberMapper.memberToMemberDto(member);
        return memberDTO;
    }


    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream().map(m ->  memberMapper.memberToMemberDto(m)).toList();
    }
}
