package com.example.Reyada.authentication;

import com.example.Reyada.authentication.config.LoginResponse;
import com.example.Reyada.authentication.entities.MyUser;
import com.example.Reyada.authentication.exception.EmailAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    AuthRepo authRepo;
    @Autowired
    private PasswordEncoder encoder;

    public MyUser register(MyUser user) {

        if (authRepo.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return authRepo.save(user);
    }



    public ResponseEntity<?> login(LoginRequest request) {
        MyUser user = authRepo.findByEmail(request.getEmail());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid credentials");
        }

        return ResponseEntity.ok(new LoginResponse(user.getId(),
                user.getName(),
                user.getEmail()));
    }
}
