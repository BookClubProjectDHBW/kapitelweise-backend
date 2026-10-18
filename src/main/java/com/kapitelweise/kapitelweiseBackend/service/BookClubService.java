package com.kapitelweise.kapitelweiseBackend.service;


import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.domain.BookClub;
import com.kapitelweise.kapitelweiseBackend.request.BookClubRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookClubDetailResponse;
import com.kapitelweise.kapitelweiseBackend.response.BookClubResponse;
import com.kapitelweise.kapitelweiseBackend.response.NewBookClubResponse;
import com.kapitelweise.kapitelweiseBackend.response.booksResponse;
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

    public BookClubService(BookclubDao bookclubDao){
        this.bookclubDao = bookclubDao;
    }


    // Club anlegen und Admin eintragen passiert atomar: schlägt addMember fehl, wird auch der Club zurückgerollt
    @Transactional
    public NewBookClubResponse registerNewBookClub(BookClubRequest request, String username){

        if(request.getName().length() > 25){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ("Eingabe zu lang!"));
        }

        if(request.getDescription().length() > 250){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ("Eingabe zu lang!"));
        }
        BookClub bookclub = bookclubDao.createBookclub(new BookClub(request.getName(), request.getDescription()));

        bookclubDao.addMember(bookclub.getId(), username, "ADMIN");


        return new NewBookClubResponse(bookclub.getId(), bookclub.getName(), bookclub.getDescription(), bookclub.getCreated_at());
    }

    public List<BookClubResponse> getClubs(String username){
        return bookclubDao.getBookClubForMember(username);
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
    public Long saveBookSuggestion (Long clubId,Long suggestionId){
        return bookclubDao.saveBooksSuggestion(clubId, suggestionId);
    }
}
