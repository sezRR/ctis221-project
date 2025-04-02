package dev.sezrr.examples.llmchatservice.auth.internal.service;

import dev.sezrr.examples.llmchatservice.auth.exposed.contract.UserService;
import dev.sezrr.examples.llmchatservice.auth.exposed.dto.NewUserRecord;
import dev.sezrr.examples.llmchatservice.shared.config.KeycloakConfig;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Keycloak keycloak;
    private final KeycloakConfig keycloakConfig;

    @Override
    public void createUser(NewUserRecord newUserRecord) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(newUserRecord.username());
        userRepresentation.setEmail(newUserRecord.email());
        userRepresentation.setFirstName(newUserRecord.firstName());
        userRepresentation.setLastName(newUserRecord.lastName());


        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(newUserRecord.password());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        userRepresentation.setCredentials(List.of(credentialRepresentation));
        
        UsersResource usersResource = getUsersResource();
        var response = usersResource.create(userRepresentation);
        
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user: " + response.getStatusInfo());
        }

        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(newUserRecord.username(), true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);
        sendVerificationEmail(userRepresentation1.getId());
    }

    @Override
    public void sendVerificationEmail(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(keycloakConfig.getRealm()).users();
    }
}
