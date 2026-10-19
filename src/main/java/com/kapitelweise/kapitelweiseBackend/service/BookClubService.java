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
    public BookRequest saveBookSuggestion (Long clubId, BookRequest request, String username){
        Long userId = userDao.getUserId(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Diese UserId existiert nicht"));

        if (!bookclubDao.isMember(userId, clubId)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Kein Mitglied dieses Buchclubs");}

        return bookclubDao.saveBooksSuggestion(clubId, request, username);
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
