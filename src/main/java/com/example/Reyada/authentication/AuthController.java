package com.example.Reyada.authentication;

import com.example.Reyada.authentication.config.LoginResponse;
import com.example.Reyada.entities.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MyUser user) {
        System.out.println("A4493289: Registering user with email: " + user.getEmail() + ", name: " + user.getName() + ", password: " + user.getPassword());
        user.setPassword(encoder.encode(user.getPassword()));
        int id = authService.register(user).getId();
        URI location = URI.create("/auth.html/register/" + id);
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        MyUser user = authService.findByEmail(request.getEmail());

        System.out.println("A4493289: " + request.getEmail());
        if (user == null ) {
            System.out.println("A4493289: Invalid credentials for " + request.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            System.out.println("A4493289: Invalid password for " + request.getEmail());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid credentials");
        }

        LoginResponse response = new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
        return ResponseEntity.ok(response);
    }
//
//    @GetMapping("/forgotPassword")
//    public ResponseEntity<?> resetPassword(@RequestBody LoginRequest request) {
//        MyUser user = authService.findByEmail(request.getEmail());
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//        authService.updatePassword(request.getEmail(), encoder.encode(request.getPassword()));
//        // Here you would typically send an email with a reset link
//        // For simplicity, we will just return a success message
//        return ResponseEntity.ok("Password reset link sent to " + request.getEmail());
//
//    }
}
