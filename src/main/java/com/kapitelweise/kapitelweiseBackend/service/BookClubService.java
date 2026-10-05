package com.kapitelweise.kapitelweiseBackend.service;


import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.domain.BookClub;
import com.kapitelweise.kapitelweiseBackend.request.BookClubRequest;
import com.kapitelweise.kapitelweiseBackend.response.NewBookClubResponse;
import org.springframework.stereotype.Service;

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
}
