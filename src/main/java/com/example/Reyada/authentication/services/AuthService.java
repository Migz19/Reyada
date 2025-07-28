package com.example.Reyada.authentication.services;

import com.example.Reyada.authentication.data.AuthRepo;
import com.example.Reyada.authentication.dto.LoginRequest;
import com.example.Reyada.authentication.dto.LoginResponse;
import com.example.Reyada.authentication.data.MyUser;
import com.example.Reyada.authentication.exception.EmailAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    AuthRepo authRepo;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    public MyUser register(MyUser user) {

        if (authRepo.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return authRepo.save(user);
    }



    public ResponseEntity<?> login(LoginRequest request, HttpServletRequest httpRequest) {
        MyUser user = authRepo.findByEmail(request.getEmail());
        if (user == null || !encoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        return ResponseEntity.ok(new LoginResponse(user.getId(), user.getName(), user.getEmail()));
    }

    public ResponseEntity<MyUser> fetchUserDetails(String email){
        System.out.println(authRepo.findByEmail(email).toString());

       return ResponseEntity.ok( authRepo.findByEmail(email));
    }
}
