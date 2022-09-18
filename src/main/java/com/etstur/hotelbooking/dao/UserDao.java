package com.etstur.hotelbooking.dao;

import com.etstur.hotelbooking.entity.User;

// Dao Pattern for User
public interface UserDao {

    User findUserByEmail(String email);

    User findByUserName(String username);

    public void saveUser(User user);

}
