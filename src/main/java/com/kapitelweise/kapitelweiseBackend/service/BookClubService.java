package com.kapitelweise.kapitelweiseBackend.service;


import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.request.BookRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookResponse;
import com.kapitelweise.kapitelweiseBackend.response.SuggestionsResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class BookClubService {
    private final BookclubDao bookclubDao;

    public BookClubService(BookclubDao bookclubDao) {
        this.bookclubDao = bookclubDao;
    }

    //speichert von found_books in books
    public SuggestionsResponse saveBookSuggestion (Long clubId, BookRequest request, String username){
        if (!bookclubDao.isMember(username, clubId)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Kein Mitglied dieses Buchclubs");}

        return bookclubDao.saveBooksSuggestion(clubId, request, username);
    }


    public List<SuggestionsResponse> returnAllSuggestions(Long clubId, String username){
        if (!bookclubDao.isMember(username, clubId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Kein Mitglied dieses Buchclubs");}

        return bookclubDao.getBookSuggestions(clubId, username);
    }


    public BookResponse getCurrentBook(Long clubId, String username){
        if(!bookclubDao.isMember(username, clubId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Du bist kein Mitglied!");
        }
        if(!bookclubDao.activeBook(clubId)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Aktuell gibts kein aktives Buch!");
        }

        return bookclubDao.getCurrentBook(clubId);
    }

    @Transactional
    public BookResponse setCurrentBook(Long clubId, Long proposalId, String username){
        if(!bookclubDao.isUserAdmin(username, clubId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Du bist kein Admin!");
        }

        if(!bookclubDao.checkExistence(clubId, proposalId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Diesen Vorschlag gibts nicht du Schlingel!");
        }

        if(!bookclubDao.checkStatus(clubId, proposalId)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Dieser Vorschlag kann nicht abgegeben werden");
        }

        if(bookclubDao.activeBook(clubId)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Aktuell gibts ein Buch!");
        }

        // falls zwei Requests gleichzeitig durch activeBook kommen, lehnt der Unique Index den zweiten ab
        try {
            return bookclubDao.saveCurrentBook(clubId, proposalId);
        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Aktuell gibts ein Buch!");
        }
    }
}
