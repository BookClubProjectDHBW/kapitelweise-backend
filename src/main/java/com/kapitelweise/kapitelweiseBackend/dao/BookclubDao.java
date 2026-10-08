package com.kapitelweise.kapitelweiseBackend.dao;

import com.kapitelweise.kapitelweiseBackend.domain.BookClub;
import com.kapitelweise.kapitelweiseBackend.response.BookClubResponse;

import java.util.Optional;

public interface BookclubDao {
     BookClub createBookclub(BookClub bookClub);

     void addMember(Long clubId, String username, String role);

    Optional<BookClubResponse> getBookClubForMember(Long clubId, String username);
}
