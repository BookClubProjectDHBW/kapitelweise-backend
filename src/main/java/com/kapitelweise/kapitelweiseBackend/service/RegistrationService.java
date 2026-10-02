package com.kapitelweise.kapitelweiseBackend.service;

import com.kapitelweise.kapitelweiseBackend.dao.UserDao;
import com.kapitelweise.kapitelweiseBackend.domain.AppUser;
import com.kapitelweise.kapitelweiseBackend.request.RegistrationRequest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RegistrationService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;


    public RegistrationService(UserDao userDao, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegistrationRequest request){
        if(userDao.userExists(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ("Benutzername bereits vergeben"));
        }

        if(request.getPassword().length() < 10){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ("ungültige Eingabe"));
        }
        String hash = passwordEncoder.encode(request.getPassword());

        try{
        userDao.saveUser(new AppUser(request.getUsername(), hash));
    }catch (DuplicateKeyException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, ("Benutzername bereits vergeben."));
        }

    }
}
