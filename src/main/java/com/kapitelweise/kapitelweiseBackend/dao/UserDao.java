package com.kapitelweise.kapitelweiseBackend.dao;
import java.util.Optional;

import com.kapitelweise.kapitelweiseBackend.domain.AppUser;
import com.kapitelweise.kapitelweiseBackend.response.BookClubResponse;
import org.springframework.web.bind.annotation.RequestParam;
//unser Interface für das UserDAO, ist ein Pattern das quasi eine Zwischenschicht zwischen Service und Persistence darstellt
// so könnten mehrere Services dieses Zeug nutzen, und wir könnten die Implementierung austauschen, ohne dass die Services was davon merken

public interface UserDao {
    boolean userExists(String username);
    void saveUser(AppUser user);
    Optional<AppUser> findByUsername(String username);

}