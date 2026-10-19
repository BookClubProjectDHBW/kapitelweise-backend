package com.kapitelweise.kapitelweiseBackend.controller;

import com.kapitelweise.kapitelweiseBackend.request.BookRequest;
import com.kapitelweise.kapitelweiseBackend.service.BookClubService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;



@RestController
public class BookClubController {


    private final BookClubService bookClubService;

    public BookClubController(BookClubService bookClubService){
        this.bookClubService = bookClubService;
    }


    // Das Prinzipal übergibt den username der Person, die den Request auslöst (Spring Security)
    // suggestion Endpunkt
    @PostMapping("/clubs/{clubId}/proposals")
    public BookRequest postSuggestion(@PathVariable("clubId") Long ClubId, @RequestBody BookRequest request, Principal principal){
        return bookClubService.saveBookSuggestion(ClubId, request, principal.getName());

    }

    // save Current Book Endpunkt
    @PostMapping("/clubs/{clubId}/proposals/{suggestionId}")
    public BookRequest postCurrentBook(@PathVariable("clubId")Long clubId, @PathVariable("suggestionId")Long suggestionId, Principal principal) {
        return bookClubService.saveCurrentBook(clubId, suggestionId, principal.getName());
    }

}
