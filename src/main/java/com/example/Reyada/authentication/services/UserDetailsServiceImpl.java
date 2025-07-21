package com.example.Reyada.authentication.services;

import com.example.Reyada.authentication.data.AuthRepo;
import com.example.Reyada.authentication.data.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AuthRepo authRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MyUser user = authRepo.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(email + " not found.");
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
