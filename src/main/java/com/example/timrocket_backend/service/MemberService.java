package com.example.timrocket_backend.service;

import com.example.timrocket_backend.domain.Member;
import com.example.timrocket_backend.repository.MemberRepository;
import com.example.timrocket_backend.service.dto.CreateMemberDTO;
import com.example.timrocket_backend.service.dto.MemberDTO;
import com.example.timrocket_backend.service.mapper.MemberMapper;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

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


}
