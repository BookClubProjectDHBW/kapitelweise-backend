package com.kapitelweise.kapitelweiseBackend.service;


import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.dao.impl.BookClubDaoImpl;
import com.kapitelweise.kapitelweiseBackend.domain.BookClub;
import com.kapitelweise.kapitelweiseBackend.request.BookClubRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookClubResponse;
import com.kapitelweise.kapitelweiseBackend.response.NewBookClubResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookClubService {
    private final BookclubDao bookclubDao;

    public BookClubService(BookclubDao bookclubDao){
        this.bookclubDao = bookclubDao;
    }


    public NewBookClubResponse registerNewBookClub(BookClubRequest request){
        Long clubId = bookclubDao.createBookclub(new BookClub(request.getName(), request.getDescription()));


        return new NewBookClubResponse(clubId, request.getName(), request.getDescription(), "ADMIN");
    }

    public Optional<BookClubResponse> getClubDetails(Long clubId, String username){
        return bookclubDao.getBookClubForMember(clubId,username);
    }
}
