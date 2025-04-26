package dev.sezrr.examples.llmchatservice.auth.internal.service;

import dev.sezrr.examples.llmchatservice.auth.exposed.contract.RoleService;
import dev.sezrr.examples.llmchatservice.auth.exposed.contract.UserService;
import dev.sezrr.examples.llmchatservice.auth.internal.config.KeycloakConfig;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final UserService userService;
    private final Keycloak keycloak;
    private final KeycloakConfig keycloakConfig;
    
    @Override
    public void assignRole(String userId, String role) {
//        UserResource user = userService.getUser(userId);
//
//        RolesResource rolesResource = keycloak.realm(keycloakConfig.getRealm()).roles();
//        
//        user.roles().realmLevel().add(List.of());
    }
}
