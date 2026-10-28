
package com.kapitelweise.kapitelweiseBackend.service;

import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.request.MeetingRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class MeetingService {

    private final BookclubDao bookclubDao;

    public MeetingService(BookclubDao bookclubDao) {
        this.bookclubDao = bookclubDao;
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
}
