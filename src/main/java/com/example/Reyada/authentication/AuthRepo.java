package com.example.Reyada.authentication;

import com.example.Reyada.authentication.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepo extends JpaRepository<MyUser,Integer> {

    MyUser findByEmail(String email);
    boolean existsByEmail(String email);
}
