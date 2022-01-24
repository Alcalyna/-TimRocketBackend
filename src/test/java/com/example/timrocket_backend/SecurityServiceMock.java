package com.example.timrocket_backend;

import com.example.timrocket_backend.security.SecurityMemberDTO;
import com.example.timrocket_backend.security.SecurityServiceInterface;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class SecurityServiceMock implements SecurityServiceInterface {

    @Override
    public String addMember(SecurityMemberDTO securityMemberDTO) {
        System.out.println("Member in the mock added");
        return null;
    }
}
