package com.kapitelweise.kapitelweiseBackend.controller;

import com.kapitelweise.kapitelweiseBackend.request.BookClubRequest;
import com.kapitelweise.kapitelweiseBackend.request.InvitationRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookClubResponse;
import com.kapitelweise.kapitelweiseBackend.response.NewBookClubResponse;
import com.kapitelweise.kapitelweiseBackend.service.BookClubService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
public class BookClubController {


    private final BookClubService bookClubService;

    public BookClubController(BookClubService bookClubService){
        this.bookClubService = bookClubService;
    }


    @PostMapping("/clubs")
    public NewBookClubResponse createNewBookClub(@RequestBody BookClubRequest request, @AuthenticationPrincipal String username){
        return bookClubService.registerNewBookClub(request, username);
    }

    // Das Prinzipal übergibt den username der Person, die den Request auslöst (Spring Security)
    @GetMapping("/clubs")
    public List<BookClubResponse> bookClubDetailsUser(Principal principal) {
        return bookClubService.getClubDetails(principal.getName());
    }

    @PostMapping("/clubs/{clubId}/invitations")
    public void invitation(@RequestBody InvitationRequest request, @AuthenticationPrincipal String username,
                           @PathVariable Long clubId){
        return;
    }

}

