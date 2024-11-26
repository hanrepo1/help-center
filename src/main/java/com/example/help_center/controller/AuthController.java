package com.example.help_center.controller;

import com.example.help_center.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<Object> generateToken(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
        return authService.generateToken(username, password, request);
    }

    @GetMapping("/validate")
    public ResponseEntity<Object> validateToken(HttpServletRequest request, @RequestParam String token) {
        return authService.validateToken(token, request);
    }

}