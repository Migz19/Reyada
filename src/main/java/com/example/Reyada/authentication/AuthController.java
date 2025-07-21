package com.example.Reyada.authentication;

import com.example.Reyada.authentication.dto.LoginRequest;
import com.example.Reyada.authentication.data.MyUser;
import com.example.Reyada.authentication.exception.EmailAlreadyExistsException;
import com.example.Reyada.authentication.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MyUser user) {
        try {
            MyUser saved = authService.register(user);
            return ResponseEntity
                    .created(URI.create("/auth/register/" + saved.getId()))
                    .body("User registered");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
       return authService.login(request);
    }


}
