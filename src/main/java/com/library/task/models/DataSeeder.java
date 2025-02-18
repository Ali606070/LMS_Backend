package com.library.task.models;


import com.library.task.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder {

    private final RoleRepository roleRepository;

    public DataSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void seedRoles() {
        if (roleRepository.count() == 0) {
            // Create a list of roles to insert
            List<Role> roles = Arrays.asList(
                    new Role(null, "ROLE_ADMIN"),
                    new Role(null, "ROLE_PATRON"),
                    new Role(null, "ROLE_LIBRARIAN")
            );

            // Save all roles in a single database operation
            roleRepository.saveAll(roles);
        }
    }
}