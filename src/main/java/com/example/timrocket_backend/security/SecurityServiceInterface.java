package com.example.timrocket_backend.security;

import org.keycloak.admin.client.resource.UserResource;

public interface SecurityServiceInterface {
    String addUser(SecurityUserDTO securityUserDTO);
    void addRole(UserResource User, String roleName);
    void removeRole(UserResource User, String roleName);
    UserResource getUser(String userId);
}
