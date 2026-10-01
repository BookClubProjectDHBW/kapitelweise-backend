package com.kapitelweise.kapitelweiseBackend.dao;
import java.util.Optional;

import com.kapitelweise.kapitelweiseBackend.domain.AppUser;


public interface UserDao {
    boolean userExists(String username);
    void saveUser(AppUser user);
    Optional<AppUser> findByUsername(String username);
}