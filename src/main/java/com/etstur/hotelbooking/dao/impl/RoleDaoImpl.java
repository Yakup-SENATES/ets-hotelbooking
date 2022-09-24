package com.etstur.hotelbooking.dao.impl;

import com.etstur.hotelbooking.dao.RoleDao;
import com.etstur.hotelbooking.entity.Role;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class RoleDaoImpl implements RoleDao {

    private static EntityManager entityManager;

    private Session currentSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public Role findRoleByName(String roleName) {
        Query<Role> query = currentSession().createQuery("from Role where name=:roleName", Role.class);

        query.setParameter("roleName", roleName);

        //check i f email is null
        Role role = null;
        try {
            role = query.getSingleResult();
        } catch (Exception ignored) {
        }

        return role;
    }
}
