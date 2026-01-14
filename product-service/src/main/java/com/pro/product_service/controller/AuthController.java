package com.pro.product_service.controller;

import com.pro.product_service.dto.AuthRequest;
import com.pro.product_service.dto.AuthResponse;
import com.pro.product_service.dto.RegisterRequest;
import com.pro.product_service.entity.Role;
import com.pro.product_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ✅ PUBLIC - Anyone can login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // ✅ PUBLIC - Anyone can register as USER
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    // ✅ ADMIN ONLY - Register with specific role
    @PostMapping("/admin/register")
    public ResponseEntity<String> registerByAdmin(
            @RequestBody RegisterRequest request,
            Authentication authentication) {

        // Extract current user's role
        String roleString = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("USER");

        Role currentUserRole = Role.valueOf(roleString);

        return ResponseEntity.ok(authService.registerByAdmin(request, currentUserRole));
    }
}
