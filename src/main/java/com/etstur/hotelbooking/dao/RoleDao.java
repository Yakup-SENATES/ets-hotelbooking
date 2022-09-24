package com.etstur.hotelbooking.dao;

import com.etstur.hotelbooking.entity.Role;

//Dao interface for Role entity
public interface RoleDao {
    public Role findRoleByName(String roleName);
}
