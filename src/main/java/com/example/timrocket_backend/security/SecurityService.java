package com.example.timrocket_backend.security;

import com.example.timrocket_backend.domain.Member;
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

@Service
public class SecurityService {
    private final RealmResource realmResource;
    private final String clientId;

    public SecurityService(Keycloak keycloak,
                           @Value("${keycloak.realm}") String realmName,
                           @Value("${keycloak.resource}") String clientId) {
        this.clientId = clientId;
        this.realmResource = keycloak.realm(realmName);
    }

    @Profile("!test")
    public String addMember(SecurityMemberDTO securityMemberDTO) {
        String createdMemberId = createMember(securityMemberDTO);
        getMember(createdMemberId).resetPassword(createCredentialRepresentation(securityMemberDTO.password()));
        addRole(getMember(createdMemberId), securityMemberDTO.role().getRoleName());
        return createdMemberId;
    }

    public void deleteMember(String MemberId) {
        getMember(MemberId).remove();
    }

    private String createMember(SecurityMemberDTO securityMemberDTO) {
        try {
            return CreatedResponseUtil.getCreatedId(createMember(securityMemberDTO.email()));
        } catch (WebApplicationException exception) {
            throw new MemberAlreadyExistsException(securityMemberDTO.email());
        }
    }

    private CredentialRepresentation createCredentialRepresentation(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    private void addRole(UserResource Member, String roleName) {
        Member.roles().clientLevel(getClientId()).add(Lists.newArrayList(getRole(roleName)));
    }

    private String getClientId() {
        return realmResource.clients().findByClientId(clientId).get(0).getId();
    }

    private Response createMember(String memberName) {
        return realmResource.users().create(createMemberRepresentation(memberName));
    }

    private UserResource getMember(String MemberId) {
        return realmResource.users().get(MemberId);
    }

    private RoleRepresentation getRole(String roleToAdd) {
        return getClientResource().roles().get(roleToAdd).toRepresentation();
    }

    private ClientResource getClientResource() {
        return realmResource.clients().get(getClientId());
    }

    private UserRepresentation createMemberRepresentation(String memberName) {
        UserRepresentation member = new UserRepresentation();
        member.setUsername(memberName);
        member.setEnabled(true);
        return member;
    }

    public Member getLoggedMember() {
        return null;
    }
}
