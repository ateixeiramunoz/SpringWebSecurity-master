package com.eoi.springwebsecurity.coreapp.repositories;

import com.eoi.springwebsecurity.coreapp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Role repository.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find by name role.
     *
     * @param name the name
     *
     * @return the role
     */
    Role findByName(String name);

}