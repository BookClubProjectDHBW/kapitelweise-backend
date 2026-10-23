package com.kapitelweise.kapitelweiseBackend.controller;

import com.kapitelweise.kapitelweiseBackend.request.BookRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookResponse;
import com.kapitelweise.kapitelweiseBackend.response.SuggestionsResponse;
import com.kapitelweise.kapitelweiseBackend.service.BookClubService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
public class BookClubController {


    private final BookClubService bookClubService;

    public BookClubController(BookClubService bookClubService){
        this.bookClubService = bookClubService;
    }


    // Das Prinzipal übergibt den username der Person, die den Request auslöst (Spring Security)
    // suggestion Endpunkt
    @PostMapping("/clubs/{clubId}/proposals")
    public SuggestionsResponse postSuggestion(@PathVariable("clubId") Long ClubId, @RequestBody BookRequest request, Principal principal){
        return bookClubService.saveBookSuggestion(ClubId, request, principal.getName());

    }

    @GetMapping("/clubs/{clubId}/suggestions")
    public List<SuggestionsResponse> getAllSuggestions(@PathVariable("clubId") Long clubId, @AuthenticationPrincipal String username){
        return bookClubService.returnAllSuggestions(clubId, username);
    }


    @PostMapping("/clubs/{clubId}/current-book/{proposalId}")
    public BookResponse setCurrentBook(@PathVariable("clubId") Long clubId, @PathVariable("proposalId") Long proposalId, @AuthenticationPrincipal String username){
        return bookClubService.setCurrentBook(clubId, proposalId, username);
    }


    @GetMapping("/clubs/{clubId}/current-book")
    public BookResponse getCurrentBook(@PathVariable("clubId") Long clubId, @AuthenticationPrincipal String username){
        return bookClubService.getCurrentBook(clubId, username);
    }

    @PatchMapping("/clubs/{clubId}/closeBook")
    public String closeCurrentBook(@PathVariable("clubId") Long clubId, @AuthenticationPrincipal String username){
        return bookClubService.closeCurrentBook(clubId, username);
    }
}
