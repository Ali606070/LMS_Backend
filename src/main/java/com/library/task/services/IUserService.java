package com.library.task.services;

import com.library.task.models.Role;
import com.library.task.models.User;

import java.util.Optional;

public interface IUserService {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<Role> findRoleByName(String roleName);
}