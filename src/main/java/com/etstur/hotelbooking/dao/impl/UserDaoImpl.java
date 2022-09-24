package com.etstur.hotelbooking.dao.impl;

import com.etstur.hotelbooking.dao.UserDao;
import com.etstur.hotelbooking.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


public class UserDaoImpl implements UserDao {

    private final EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Session currentSession() {
        return entityManager.unwrap(Session.class);
    }


    @Override
    public User findUserByEmail(String email) {
        Query<User> query = currentSession().createQuery("from User where email=:email", User.class);

         query.setParameter("email", email);

        //check i f email is null
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception ignored) {
        }

        return user;
    }

    @Override
    public User findByUserName(String username) {
        Query<User> query = currentSession().createQuery("from User where userName=:username", User.class);

        query.setParameter("username", username);

        //check if valid user and is existed or null
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception ignored) {
        }

        return user;
    }

    @Override
    public void saveUser(User user) {

    }
}
