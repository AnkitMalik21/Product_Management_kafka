package com.pro.product_service.service;

import com.pro.product_service.dto.AuthRequest;
import com.pro.product_service.dto.AuthResponse;
import com.pro.product_service.dto.RegisterRequest;
import com.pro.product_service.entity.Role;
import com.pro.product_service.entity.User;
import com.pro.product_service.repository.UserRepository;
import com.pro.product_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // ✅ LOGIN - Used by everyone
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername(), String.valueOf(user.getRole()));

        return new AuthResponse(token, user.getUsername(), user.getRole());
    }

    // ✅ PUBLIC REGISTRATION - Creates USER only
    public String registerUser(AuthRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);  // ✅ Always USER

        userRepository.save(user);
        return "User registered successfully";
    }

    // ✅ ADMIN-ONLY REGISTRATION - Creates USER or ADMIN based on request
    public String registerByAdmin(RegisterRequest request, Role currentUserRole) {
        // Security check: Only ADMIN can use this method
        if (currentUserRole != Role.ADMIN) {
            throw new RuntimeException("Only ADMIN can register users with specific roles");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());  // ✅ Role from request (USER or ADMIN)

        userRepository.save(user);
        return "User registered successfully with role: " + request.getRole();
    }
}
