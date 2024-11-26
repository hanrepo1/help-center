package com.example.help_center.service;

import com.example.help_center.model.User;
import com.example.help_center.repository.UserRepository;
import com.example.help_center.security.Crypto;
import com.example.help_center.util.JwtUtil;
import com.example.help_center.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<Object> generateToken(String username, String password, HttpServletRequest request) {
        User user = userRepository.findByUsername(username);
        if (user != null){
            String unlockedPassword = Crypto.performEncrypt(password);
            if (user.getPassword().equals(unlockedPassword)){
                Map<String,Object> mapForClaims = new HashMap<>();
                mapForClaims.put("uid",user.getId());//payload
                mapForClaims.put("email",user.getEmail());//payload
//                mapForClaims.put("password",user.getPassword());
                mapForClaims.put("role", user.getRoleId().getName());//payload
                String token = jwtUtil.generateToken(username, mapForClaims);
                return ResponseUtil.dataFound("Data Found", token, request);
            }
            return ResponseUtil.dataNotFound("Password does not match.", request);
        }
        return ResponseUtil.dataNotFound("Username not found.", request);
    }

    public ResponseEntity<Object> validateToken(String token, HttpServletRequest request) {
        boolean isValid = jwtUtil.validateToken(token);
        if (isValid){
            return ResponseUtil.dataFound("Data Found",token, request);
        } else {
            return ResponseUtil.dataNotFound("Data not found", request);
        }
    }

}
