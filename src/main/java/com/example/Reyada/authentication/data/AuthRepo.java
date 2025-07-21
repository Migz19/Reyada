package com.example.Reyada.authentication.data;

import com.example.Reyada.authentication.data.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepo extends JpaRepository<MyUser,Integer> {

    MyUser findByEmail(String email);
    boolean existsByEmail(String email);
}
