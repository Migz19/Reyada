package com.example.Reyada.authentication;

import com.example.Reyada.authentication.dto.LoginRequest;
import com.example.Reyada.authentication.data.MyUser;
import com.example.Reyada.authentication.exception.EmailAlreadyExistsException;
import com.example.Reyada.authentication.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httprequest) {
       return authService.login(request,httprequest);
    }

    @GetMapping("/user")
    public ResponseEntity<MyUser> fetchUserDetails(
                                                   @AuthenticationPrincipal UserDetails user) {
        System.out.println("Fetching user details for: " + user.getUsername());
        return authService.fetchUserDetails(user.getUsername());
    }

}
