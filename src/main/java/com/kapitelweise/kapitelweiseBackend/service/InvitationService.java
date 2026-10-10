package com.kapitelweise.kapitelweiseBackend.service;

import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.dao.InvitationDao;
import com.kapitelweise.kapitelweiseBackend.dao.UserDao;
import com.kapitelweise.kapitelweiseBackend.domain.Invitation;
import com.kapitelweise.kapitelweiseBackend.domain.InvitationStatus;
import com.kapitelweise.kapitelweiseBackend.request.InvitationFeedbackRequest;
import com.kapitelweise.kapitelweiseBackend.request.InvitationRequest;
import com.kapitelweise.kapitelweiseBackend.response.InvitationFeedbackResponse;
import com.kapitelweise.kapitelweiseBackend.response.InvitationResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.Objects;


@Service
public class InvitationService {
    private final InvitationDao invitationDao;
    private final UserDao userDao;
    private final BookclubDao bookclubDao;

    public InvitationService(InvitationDao invitationDao, UserDao userDao, BookclubDao bookclubDao){
        this.invitationDao = invitationDao;
        this.userDao = userDao;
        this.bookclubDao = bookclubDao;
    }


    @Transactional
    public InvitationResponse registerNewInvitation(InvitationRequest request, String username, Long clubId){
        if(!bookclubDao.bookclubExists(clubId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Buchclub nicht gefunden");
        }
        if(request.getUsername() == null || request.getUsername().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bitte gib einen Benutzernamen ein.");
        }
        Long invitedById = userDao.getUserId(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Diese UserId existiert nicht."));

        if(!invitationDao.isUserAdmin(invitedById, clubId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Du bist kein Admin!");
        }

        Long invitedUserId = userDao.getUserId(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Benutzer nicht gefunden."));


        if(invitationDao.invitationPending(invitedUserId, clubId)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Der User wurde schon eingeladen du Vogel");
        }

        if(invitationDao.isAlreadyMember(invitedUserId, clubId)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Der User ist schon Member du Vogel");
        }
        try{
            Invitation saved = invitationDao.createInvitation(
                    new Invitation(clubId, invitedUserId, invitedById, InvitationStatus.PENDING));

            return new InvitationResponse(
                    saved.getId(),
                    saved.getInvitationStatus().name(),
                    request.getUsername(),
                    saved.getCreated_at());
        }
        catch(DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Der User wurde schon eingeladen.");
        }
    }

    @Transactional
    public InvitationFeedbackResponse updatePendingInvitation(InvitationFeedbackRequest request, String username, Long invitationId){
       String newStatus = request.getInvitationStatus();
        if (newStatus == null
                || (!newStatus.trim().equalsIgnoreCase("ACCEPTED")
                && !newStatus.trim().equalsIgnoreCase("DECLINED"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Status muss ACCEPTED oder DECLINED sein");
        }
        InvitationStatus newFormattedStatus = InvitationStatus.valueOf(newStatus.trim().toUpperCase());

        Invitation currentInvitation = invitationDao.getInvitation(invitationId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Diese Einladung wurde nicht gefunden"));

            Long invitedUserId = userDao.getUserId(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Benutzer nicht gefunden."));

            if(!Objects.equals(currentInvitation.getInvitedUserId(), invitedUserId)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Dieser User wurde nicht zu diesem Buchclub eingeladen");
            }
        if (currentInvitation.getInvitationStatus() != InvitationStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Diese Einladung wurde bereits beantwortet");
        }

        String clubName = bookclubDao.getBookClubName(currentInvitation.getBookClubId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Buchclub nicht gefunden"));

        invitationDao.changeStatus(currentInvitation.getId(), newFormattedStatus);
        if(newFormattedStatus == InvitationStatus.ACCEPTED) {
            try {
                invitationDao.addMember(invitedUserId, currentInvitation.getBookClubId());
            } catch (DuplicateKeyException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Der User ist bereits Mitglied");
            }
        }
        return new InvitationFeedbackResponse(
                currentInvitation.getId(),
                newFormattedStatus.name(),
                currentInvitation.getBookClubId(),
                clubName,
                LocalDateTime.now().toString());
    }
}
