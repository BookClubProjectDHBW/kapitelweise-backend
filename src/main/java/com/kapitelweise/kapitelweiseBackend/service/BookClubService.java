package com.kapitelweise.kapitelweiseBackend.service;


import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.dao.UserDao;
import com.kapitelweise.kapitelweiseBackend.request.BookRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookClubDetailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class BookClubService {
    private final BookclubDao bookclubDao;

    public BookClubService(BookclubDao bookclubDao) {
        this.bookclubDao = bookclubDao;
    }




    //speichert von found_books in books
    public BookRequest saveBookSuggestion (Long clubId, BookRequest request, String username){
        if (!bookclubDao.isMember(username, clubId)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Kein Mitglied dieses Buchclubs");}

        if (!openLibraryService.existsInFoundBooks(request)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Buchdaten stimmen nicht mit den gefundenen Büchern überein");
        }

        return bookclubDao.saveBooksSuggestion(clubId, request, username);
    }


    public BookRequest saveCurrentBook(Long clubId, Long suggestionId, String username){
        if(!bookclubDao.isUserAdmin(username, clubId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Du bist kein Admin!");
        }

        return null;
        //return bookclubDao.saveCurrentBook(clubId, suggestionId, username);
    }

}
