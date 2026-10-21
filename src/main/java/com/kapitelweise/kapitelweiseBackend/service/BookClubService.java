package com.kapitelweise.kapitelweiseBackend.service;


import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.request.BookRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookResponse;
import com.kapitelweise.kapitelweiseBackend.response.SuggestionsResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class BookClubService {
    private final BookclubDao bookclubDao;
    private final AmazonSQSService amazonSQSService;

    public BookClubService(BookclubDao bookclubDao, AmazonSQSService amazonSQSService) {

        this.bookclubDao = bookclubDao;
        this.amazonSQSService = amazonSQSService;

    }




    @Transactional
    public SuggestionsResponse saveBookSuggestion (Long clubId, BookRequest request, String username){
        if (!bookclubDao.isMember(username, clubId)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Kein Mitglied dieses Buchclubs");}

        if(request.getIsbn().isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Leere ISBN gibts nicht Kumpel!");

        bookclubDao.deleteSuggestion(username, clubId);

        // bei einem Doppelklick kommen zwei Requests gleichzeitig durch, der Unique Index lehnt den zweiten ab
        SuggestionsResponse response;
        try {
            response = bookclubDao.saveBooksSuggestion(clubId, request, username);
        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dein Vorschlag wird gerade schon gespeichert!");
        }

        amazonSQSService.sendSuggestionCheck("SEND_SUGGESTION", response.getId(),clubId, response.getIsbn());

        return response;

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
