package com.kapitelweise.kapitelweiseBackend.controller;

import com.kapitelweise.kapitelweiseBackend.domain.BookClub;
import com.kapitelweise.kapitelweiseBackend.request.BookClubRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookClubResponse;
import com.kapitelweise.kapitelweiseBackend.response.NewBookClubResponse;
import com.kapitelweise.kapitelweiseBackend.service.BookClubService;
import com.kapitelweise.kapitelweiseBackend.service.RegistrationService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;


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

    // Das Prinzipal übergibt den username der Person, die den Request auslöst (Spring Security)
    @GetMapping("/clubDetails")
    public Optional<BookClubResponse> bookClubDetails(@RequestParam("clubId") Long clubId, Principal principal) {
        return bookClubService.getClubDetails(clubId, principal.getName());
    }
}