package dev.sezrr.examples.llmchatservice.auth.internal.controller.v1;

import dev.sezrr.examples.llmchatservice.auth.exposed.contract.UserService;
import dev.sezrr.examples.llmchatservice.auth.exposed.dto.NewUserRecord;
import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;
    
    @PostMapping
    public ResponseEntity<CustomResponseEntity<?>> createUser(@RequestBody NewUserRecord newUserRecord) {
        userService.createUser(newUserRecord);
        return ResponseEntity.ok(CustomResponseEntity.success("User created successfully"));
    }
    
    // TODO: VULNERABILITY: Spamming verification emails
    @PutMapping("/{userId}/verify")
    public ResponseEntity<CustomResponseEntity<?>> sendVerificationEmail(@PathVariable String userId) {
        userService.sendVerificationEmail(userId);
        return ResponseEntity.ok(CustomResponseEntity.success("Verification email sent successfully"));
    }
}
