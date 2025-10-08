package com.example.management.controllers;

import com.example.management.dto.AuthRequest;
import com.example.management.dto.AuthResponse;
import com.example.management.dto.UserDTO;
import com.example.management.entities.enums.Roles;
import com.example.management.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/addAdmin")
    public ResponseEntity<String> addAdmin(@RequestBody UserDTO userDTO){
        userDTO.setRole(Roles.ADMIN);
        return ResponseEntity.ok(authService.signup(userDTO));
    }

    @PostMapping("/addSupplier")
    public ResponseEntity<String> addSupplier(@RequestBody UserDTO userDTO){
        userDTO.setRole(Roles.SUPPLIER);
        return ResponseEntity.ok(authService.signup(userDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO userDTO) {
        userDTO.setRole(Roles.USER);
        return ResponseEntity.ok(authService.signup(userDTO));
    }
}
