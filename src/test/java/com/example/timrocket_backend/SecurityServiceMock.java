package com.example.timrocket_backend;

import com.example.timrocket_backend.security.SecurityServiceInterface;
import com.example.timrocket_backend.security.SecurityUserDTO;
import org.keycloak.admin.client.resource.UserResource;
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

    @Override
    public void addRole(UserResource User, String roleName) {

    }

    @Override
    public void removeRole(UserResource User, String roleName) {

    }

    @Override
    public UserResource getUser(String userId) {
        return null;
    }
}
