package com.etstur.hotelbooking.services;

import com.etstur.hotelbooking.entity.User;
import com.etstur.hotelbooking.temp.CurrentUser;
import org.springframework.security.core.userdetails.UserDetailsService;

//service pattern for user
public interface UserService extends UserDetailsService {

    public User findUserByEmail(String email);

    public void saveUser(CurrentUser user);

    public int getLoggedUserId();

}
