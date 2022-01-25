package com.example.timrocket_backend.security;

import com.example.timrocket_backend.domain.User;
import com.google.common.collect.Lists;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Profile("!test")
@Service
public class SecurityService implements SecurityServiceInterface{
    private final RealmResource realmResource;
    private final String clientId;

    public SecurityService(Keycloak keycloak,
                           @Value("${keycloak.realm}") String realmName,
                           @Value("${keycloak.resource}") String clientId) {
        this.clientId = clientId;
        this.realmResource = keycloak.realm(realmName);
    }

    @Override
    public String addUser(SecurityUserDTO securityUserDTO) {
        String createdUserId = createUser(securityUserDTO);
        getUser(createdUserId).resetPassword(createCredentialRepresentation(securityUserDTO.password()));
        addRole(getUser(createdUserId), securityUserDTO.securityRole().getRoleName());
        return createdUserId;
    }

    public void deleteUser(String userId) {
        getUser(userId).remove();
    }

    private String createUser(SecurityUserDTO securityUserDTO) {
        try {
            return CreatedResponseUtil.getCreatedId(createUser(securityUserDTO.email()));
        } catch (WebApplicationException exception) {
            throw new UserAlreadyExistsException(securityUserDTO.email());
        }
    }

    private CredentialRepresentation createCredentialRepresentation(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    private void addRole(UserResource User, String roleName) {
        User.roles().clientLevel(getClientId()).add(Lists.newArrayList(getRole(roleName)));
    }

    private String getClientId() {
        return realmResource.clients().findByClientId(clientId).get(0).getId();
    }

    private Response createUser(String userName) {
        return realmResource.users().create(createUserRepresentation(userName));
    }

    private UserResource getUser(String userId) {
        return realmResource.users().get(userId);
    }

    private RoleRepresentation getRole(String roleToAdd) {
        return getClientResource().roles().get(roleToAdd).toRepresentation();
    }

    private ClientResource getClientResource() {
        return realmResource.clients().get(getClientId());
    }

    private UserRepresentation createUserRepresentation(String userName) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userName);
        user.setEnabled(true);
        return user;
    }

    public User getLoggedUser() {
        return null;
    }
}
