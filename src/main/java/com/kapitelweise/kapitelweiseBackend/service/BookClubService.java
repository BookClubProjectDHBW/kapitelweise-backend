package com.kapitelweise.kapitelweiseBackend.service;


import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.request.BookRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookResponse;
import com.kapitelweise.kapitelweiseBackend.response.SuggestionsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class BookClubService {
    private static final Logger log = LoggerFactory.getLogger(BookClubService.class);

    private final BookclubDao bookclubDao;
    private final AmazonSQSService amazonSQSService;

    public BookClubService(BookclubDao bookclubDao, AmazonSQSService amazonSQSService) {

        this.bookclubDao = bookclubDao;
        this.amazonSQSService = amazonSQSService;

    }




    @Transactional
    public SuggestionsResponse saveBookSuggestion (Long clubId, BookRequest request, String username){
        if (!bookclubDao.isMember(username, clubId)) {
        log.warn(username + " ist kein Mitglied von Club " + clubId);
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Kein Mitglied dieses Buchclubs");}

        if(request.getIsbn() == null || request.getIsbn().isBlank() || request.getTitle() == null || request.getTitle().isBlank()) {
            log.warn("Vorschlag von " + username + " ohne ISBN abgelehnt");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Leere ISBN gibts nicht Kumpel!");
        }

        bookclubDao.deleteSuggestion(username, clubId);
        log.info("Alter Vorschlag von " + username + " in Club " + clubId + " entfernt");

        SuggestionsResponse response;
        try {
            response = bookclubDao.saveBooksSuggestion(clubId, request, username);
        } catch (DuplicateKeyException e) {
            log.warn("Vorschlag von " + username + " in Club " + clubId + " wird schon gespeichert");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dein Vorschlag wird gerade schon gespeichert!");
        }
        log.info("Vorschlag " + response.getId() + " von " + username + " in Club " + clubId + " gespeichert");

        amazonSQSService.sendSuggestionCheck("SEND_SUGGESTION", response.getId(),clubId, response.getIsbn());

        return response;

    }


    public List<SuggestionsResponse> returnAllSuggestions(Long clubId, String username){
        if (!bookclubDao.isMember(username, clubId)) {
            log.warn(username + " ist kein Mitglied von Club " + clubId);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Kein Mitglied dieses Buchclubs");}

        List<SuggestionsResponse> suggestions = bookclubDao.getBookSuggestions(clubId, username);
        log.info(suggestions.size() + " Vorschläge für Club " + clubId + " geladen");
        return suggestions;
    }


    public BookResponse getCurrentBook(Long clubId, String username){
        if(!bookclubDao.isMember(username, clubId)){
            log.warn(username + " ist kein Mitglied von Club " + clubId);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Du bist kein Mitglied!");
        }
        if(!bookclubDao.activeBook(clubId)){
            log.info("Club " + clubId + " hat gerade kein aktuelles Buch");
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Aktuell gibts kein aktives Buch!");
        }

        return bookclubDao.getCurrentBook(clubId);
    }

    @Transactional
    public BookResponse setCurrentBook(Long clubId, Long proposalId, String username){
        if(!bookclubDao.isUserAdmin(username, clubId)){
            log.warn(username + " ist kein Admin von Club " + clubId);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Du bist kein Admin!");
        }

        if(!bookclubDao.checkExistence(clubId, proposalId)){
            log.warn("Vorschlag " + proposalId + " gibt es in Club " + clubId + " nicht");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Diesen Vorschlag gibts nicht du Schlingel!");
        }

        if(!bookclubDao.checkStatus(clubId, proposalId)){
            log.warn("Vorschlag " + proposalId + " ist nicht READY");
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Dieser Vorschlag kann nicht abgegeben werden");
        }

        if(bookclubDao.activeBook(clubId)){
            log.warn("Club " + clubId + " hat schon ein aktuelles Buch");
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Aktuell gibts ein Buch!");
        }

        // falls zwei Requests gleichzeitig durch activeBook kommen, lehnt der Unique Index den zweiten ab
        try {
            BookResponse book = bookclubDao.saveCurrentBook(clubId, proposalId);
            log.info("Vorschlag " + proposalId + " ist jetzt das aktuelle Buch von Club " + clubId);

            bookclubDao.deleteSuggestions(clubId);
            log.info("Löschen im " + clubId + "hat auch geklappt");
                return book;
        } catch (DuplicateKeyException e) {
            log.warn("Club " + clubId + " hat schon ein aktuelles Buch");
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Aktuell gibts ein Buch!");
        }
    }

    @Transactional
    public String closeCurrentBook(Long clubId, String username){
        if(!bookclubDao.isUserAdmin(username, clubId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Du bist kein Admin!");
        }
        if(!bookclubDao.activeBook(clubId)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Aktuell gibt es kein offenes Buch!");
        }
            return bookclubDao.deleteBook(clubId);

    }
}

