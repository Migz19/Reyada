package com.example.Reyada.authentication;

import com.example.Reyada.authentication.config.UserDetailsServiceImpl;
import com.example.Reyada.entities.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    AuthRepo authRepo;
    public MyUser register(MyUser user) {

        if (user.getEmail() == null || user.getPassword() == null || user.getName() == null) {
            user.setName("annonyonmous");
           // throw new IllegalArgumentException("Email, password, and name must not be null");
        }
        if (user.getEmail().isEmpty()||user.getName().isEmpty()|| user.getPassword().isEmpty()) {
            user.setName("annonyonmous");
           // throw new IllegalArgumentException("Email, password, and name must not be empty");

    }
        if (authRepo.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        return authRepo.save(user);
    }

    public MyUser findByEmail(String email){
        return authRepo.findByEmail(email);
    }
//    public void updatePassword(String email,String newPassword){
//        authRepo.updatePassword(email, newPassword);
//    }
}
