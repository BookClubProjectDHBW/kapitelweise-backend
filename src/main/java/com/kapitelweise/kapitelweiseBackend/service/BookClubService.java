package com.kapitelweise.kapitelweiseBackend.service;


import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.response.BookClubDetailResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookClubService {
    private final BookclubDao bookclubDao;

    public BookClubService(BookclubDao bookclubDao){
        this.bookclubDao = bookclubDao;
    }


    public BookClubDetailResponse getBookClubDetails(Long clubId, String username){
        try{
            return bookclubDao.getClubDetails(clubId, username);
        }
        catch(EmptyResultDataAccessException e){
           throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Kein Mitglied dieses Buchclubs");
        }

    }
}
