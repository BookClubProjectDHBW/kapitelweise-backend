package com.kapitelweise.kapitelweiseBackend.service;


import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.domain.BookClub;
import com.kapitelweise.kapitelweiseBackend.request.BookClubRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookClubDetailResponse;
import com.kapitelweise.kapitelweiseBackend.response.BookClubResponse;
import com.kapitelweise.kapitelweiseBackend.response.NewBookClubResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        BookClub bookclub = bookclubDao.createBookclub(new BookClub(request.getName(), request.getDescription()));

        bookclubDao.addMember(bookclub.getId(), username, "ADMIN");


        return new NewBookClubResponse(bookclub.getId(), bookclub.getName(), bookclub.getDescription(), bookclub.getCreated_at());
    }

    public List<BookClubResponse> getClubs(String username){
        return bookclubDao.getBookClubForMember(username);
    }


    public BookClubDetailResponse getBookClubDetails(Long clubId, String username){
        return bookclubDao.getClubDetails(clubId, username);
    }
}
