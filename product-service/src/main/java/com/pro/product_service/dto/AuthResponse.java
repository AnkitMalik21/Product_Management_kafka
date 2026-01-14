package com.pro.product_service.dto;

import com.pro.product_service.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String type = "Bearer ";
    private String username;
    private Role role;

    public AuthResponse(String token,String username,Role role){
        this.token = token;
        this.username = username;
        this.role = role;
    }
}
