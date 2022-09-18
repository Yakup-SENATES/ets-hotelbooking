package com.etstur.hotelbooking.repository;

import com.etstur.hotelbooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByUserName(String username);
}
