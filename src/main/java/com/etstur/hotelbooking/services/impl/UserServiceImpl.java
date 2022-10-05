package com.etstur.hotelbooking.services.impl;

import com.etstur.hotelbooking.entity.Role;
import com.etstur.hotelbooking.entity.User;
import com.etstur.hotelbooking.repository.RoleRepository;
import com.etstur.hotelbooking.repository.UserRepository;
import com.etstur.hotelbooking.services.UserService;
import com.etstur.hotelbooking.temp.CurrentUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    // Service pattern to manage transactions
    // and handle services for user between server and client

    private final UserRepository userRepository;
    private final RoleRepository repository;
    private static BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository repository) {
        this.userRepository = userRepository;
        this.repository = repository;
    }

    @Override
    @Transactional
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void saveUser(CurrentUser user) {
        User newUser = new User();

        //bcrypt password to save it hashing in database
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setUserName(user.getUserName());
        newUser.setEmail(user.getEmail());

        //give user default role
        newUser.setRoles(List.of(repository.findByName("ROLE_USER")));
        userRepository.save(newUser);
    }

    @Override
    @Transactional
    public Long getLoggedUserId() {
        User user = userRepository.findByEmail(loggedUserEmail());
        return user.getId();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
               mapRolesToAuthorities(user.getRoles()));
    }

    //authority role for user

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    // get current logged user email using spring security

    private String loggedUserEmail() {

        Object principal = SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        return principal.toString();
    }
}
