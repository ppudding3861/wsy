package com.pickfit.pickfit.oauth2.controller;

import com.pickfit.pickfit.oauth2.model.dto.AuthResponse;
import com.pickfit.pickfit.oauth2.model.entity.UserEntity;
import com.pickfit.pickfit.oauth2.security.TokenProvider;
import com.pickfit.pickfit.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @GetMapping("/oauth2/callback")
    public ResponseEntity<?> authenticate(@RequestParam("token") String token) {
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/api/auth/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            return ResponseEntity.notFound().build();
        }

        String email = oAuth2User.getAttribute("email");
        UserEntity user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("email", user.getEmail());
        response.put("name", user.getName());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/auth/me/name")
    public ResponseEntity<?> updateUserName(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            @RequestBody Map<String, String> payload) {

        String email = oAuth2User.getAttribute("email");
        String newName = payload.get("name");

        UserEntity user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user = userService.updateUserName(user.getId(), newName);

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("email", user.getEmail());
        response.put("name", user.getName());

        return ResponseEntity.ok(response);
    }
}