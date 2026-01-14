package com.pro.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false,length = 50)
    private String username;

    @Column(nullable = false) //  “This column must NOT allow NULL values in the database.”
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length=20)
    private Role role;
}
