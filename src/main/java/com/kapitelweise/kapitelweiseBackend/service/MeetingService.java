
package com.kapitelweise.kapitelweiseBackend.service;

import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.request.MeetingRequest;
import com.kapitelweise.kapitelweiseBackend.response.UserMeetingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.kapitelweise.kapitelweiseBackend.dao.MeetingDao;
import com.kapitelweise.kapitelweiseBackend.response.MeetingResponse;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingService {

private final BookclubDao bookclubDao;
private final MeetingDao meetingDao;

public MeetingService(BookclubDao bookclubDao, MeetingDao meetingDao) {
    this.bookclubDao = bookclubDao;
    this.meetingDao = meetingDao;
}
@Transactional
public MeetingResponse createMeeting(Long clubId, MeetingRequest request, String username) {
    validateMeetingCreation(clubId, request, username);
    return meetingDao.saveMeeting(clubId, request);
}
    public void validateMeetingCreation(Long clubId, MeetingRequest request, String username) {

        if (!bookclubDao.isUserAdmin(username, clubId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Nur der Admin darf Treffen erstellen!"
            );
        }

        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Meeting-Daten fehlen!"
            );
        }

        if (request.getTitle() == null || request.getTitle().isBlank()
                || request.getTitle().length() > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Der Titel muss zwischen 1 und 100 Zeichen enthalten!"
            );
        }

        if (request.getDescription() != null
                && request.getDescription().length() > 500) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Die Beschreibung darf maximal 500 Zeichen enthalten!"
            );
        }

        if (request.getStartsAt() == null
                || !request.getStartsAt().isAfter(LocalDateTime.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Das Treffen muss in der Zukunft liegen!"
            );
        }

        if (request.getEndsAt() != null
                && !request.getEndsAt().isAfter(request.getStartsAt())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Das Ende muss nach dem Beginn liegen!"
            );
        }

        if (request.getLocation() != null
                && request.getLocation().length() > 255) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Der Ort darf maximal 255 Zeichen enthalten!"
            );
        }
    }

    public MeetingResponse getNextMeeting(Long clubId,String username){
        if(!bookclubDao.isMember(username, clubId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Du bist kein Mitglied des Clubs");
        }
        return meetingDao.getMeetingForMember(clubId);
    }

    public String acceptMeeting(Long clubId, Long meetingId, String username, String responseStatus){
        if (responseStatus == null || !(responseStatus.equals("ACCEPTED") || responseStatus.equals("DECLINED"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status fehlt oder Wert ist nicht erlaubt");
        }
        if (!bookclubDao.isMember(username, clubId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Kein Mitglied");
        }
        if (!meetingDao.meetingExists(clubId, meetingId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Das Meeting gibt es in diesem Club nicht");
        }
        if (meetingDao.meetingHasStarted(clubId, meetingId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Meeting hat schon begonnen");
        }

        return meetingDao.saveAcceptMeeting(clubId, meetingId, username, responseStatus);
    }


    public List<UserMeetingResponse> getUserResponse(Long clubId, String username){
    if(!bookclubDao.isUserAdmin(username, clubId)){
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Du bist kein Admin des Buchclubs");
    }
    return meetingDao.getUserMeetingResponse(clubId);
    }
}

