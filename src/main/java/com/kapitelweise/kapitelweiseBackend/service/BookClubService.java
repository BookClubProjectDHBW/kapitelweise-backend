package com.kapitelweise.kapitelweiseBackend.service;


import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.domain.BookClub;
import com.kapitelweise.kapitelweiseBackend.request.BookClubRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookClubDetailResponse;
import com.kapitelweise.kapitelweiseBackend.response.BookClubResponse;
import com.kapitelweise.kapitelweiseBackend.response.NewBookClubResponse;
import org.springframework.http.HttpStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BookClubService {
    private final BookclubDao bookclubDao;
    private final UserDao userDao;

    public BookClubService(BookclubDao bookclubDao, UserDao userDao) {
        this.bookclubDao = bookclubDao;
        this.userDao = userDao;
    }


    public BookClubDetailResponse getBookClubDetails(Long clubId, String username){
        try{
            return bookclubDao.getClubDetails(clubId, username);
        }
        catch(EmptyResultDataAccessException e){
           throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Kein Mitglied dieses Buchclubs");
        }
    }


    //speichert von found_books in books
    public BookRequest saveBookSuggestion (Long clubId, BookRequest request){
        return bookclubDao.saveBooksSuggestion(clubId, request);
    }

    public BookRequest saveCurrentBook(Long clubId, Long suggestionId, String username){
        Long invitedById = userDao.getUserId(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Diese UserId existiert nicht."));

        if(!bookclubDao.isUserAdmin(invitedById, clubId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Du bist kein Admin!");
        }

        return null;
        //return bookclubDao.saveCurrentBook(clubId, suggestionId, username);
    }

}
