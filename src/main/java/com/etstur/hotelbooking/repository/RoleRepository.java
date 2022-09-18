package com.etstur.hotelbooking.repository;

import com.etstur.hotelbooking.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {


    Role findByName(String name);

}
