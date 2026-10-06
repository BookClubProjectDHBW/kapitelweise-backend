package com.kapitelweise.kapitelweiseBackend.controller;

import com.kapitelweise.kapitelweiseBackend.request.BookClubRequest;
import com.kapitelweise.kapitelweiseBackend.response.NewBookClubResponse;
import com.kapitelweise.kapitelweiseBackend.service.BookClubService;
import com.kapitelweise.kapitelweiseBackend.service.RegistrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BookClubController {


    private final BookClubService bookClubService;

    public BookClubController(BookClubService bookClubService){
        this.bookClubService = bookClubService;
    }


    @PostMapping("/clubs")
    public NewBookClubResponse createNewBookClub(@RequestBody BookClubRequest request){
        return bookClubService.registerNewBookClub(request);
    }
}