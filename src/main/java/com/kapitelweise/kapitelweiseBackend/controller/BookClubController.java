package com.kapitelweise.kapitelweiseBackend.controller;

import com.kapitelweise.kapitelweiseBackend.request.BookClubRequest;
import com.kapitelweise.kapitelweiseBackend.request.InvitationFeedbackRequest;
import com.kapitelweise.kapitelweiseBackend.request.InvitationRequest;
import com.kapitelweise.kapitelweiseBackend.response.*;
import com.kapitelweise.kapitelweiseBackend.service.BookClubService;
import com.kapitelweise.kapitelweiseBackend.service.InvitationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
public class BookClubController {


    private final BookClubService bookClubService;
    private final InvitationService invitationService;

    public BookClubController(BookClubService bookClubService, InvitationService invitationService){
        this.bookClubService = bookClubService;
        this.invitationService = invitationService;
    }


    @PostMapping("/clubs")
    public NewBookClubResponse createNewBookClub(@RequestBody BookClubRequest request, @AuthenticationPrincipal String username){
        return bookClubService.registerNewBookClub(request, username);
    }

    // Das Prinzipal übergibt den username der Person, die den Request auslöst (Spring Security)
    @GetMapping("/clubs")
    public List<BookClubResponse> bookClubDetailsUser(Principal principal) {
        return bookClubService.getClubs(principal.getName());
    }

    @GetMapping("/clubs/{clubId}")
    public BookClubDetailResponse bookClubDetails(@PathVariable("clubId") Long clubID, Principal principal){
        return bookClubService.getBookClubDetails(clubID,principal.getName());
    }

    @PostMapping("/clubs/{clubId}/invitations")
    public InvitationResponse invitation(@RequestBody InvitationRequest request, @AuthenticationPrincipal String username,
                                         @PathVariable("clubId") Long clubId){
        return invitationService.registerNewInvitation(request, username, clubId);
    }


    @PatchMapping("/clubs/invitations/{invitationId}")
    public InvitationFeedbackResponse invitationFeedback(@RequestBody InvitationFeedbackRequest request, @AuthenticationPrincipal String username,
                                                         @PathVariable("invitationId") Long invitationId){
        return invitationService.updatePendingInvitation(request, username, invitationId);
    }
    @GetMapping("/clubs/invitations")
    public List<InvitationUserResponse> getInvitations( Principal principal){
        return invitationService.getInvitations(principal.getName());


    }
    /*
    @PatchMapping("/clubs/{clubId}")
    public CloseBooksResponse updateBookStatus(Long clubId,Long bookId, Principal principal,String bookStatus){
        return BookClubService.updateCurrentBooks()
    }

     */


}

