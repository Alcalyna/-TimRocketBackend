package com.example.timrocket_backend;

import com.example.timrocket_backend.security.SecurityServiceInterface;
import com.example.timrocket_backend.security.SecurityUserDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class SecurityServiceMock implements SecurityServiceInterface {

    @Override
    public String addUser(SecurityUserDTO securityUserDTO) {
        System.out.println("Member in the mock added");
        return null;
    }
}
