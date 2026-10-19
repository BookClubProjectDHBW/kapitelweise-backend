package com.kapitelweise.kapitelweiseBackend.service;

import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.dao.InvitationDao;
import com.kapitelweise.kapitelweiseBackend.dao.UserDao;
import com.kapitelweise.kapitelweiseBackend.domain.Invitation;
import com.kapitelweise.kapitelweiseBackend.domain.InvitationStatus;
import com.kapitelweise.kapitelweiseBackend.request.InvitationRequest;
import com.kapitelweise.kapitelweiseBackend.response.InvitationResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


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
}
