package com.kapitelweise.kapitelweiseBackend.dao;

import com.kapitelweise.kapitelweiseBackend.domain.BookClub;
import com.kapitelweise.kapitelweiseBackend.response.BookClubDetailResponse;
import com.kapitelweise.kapitelweiseBackend.response.BookClubResponse;

import java.util.List;
import java.util.Optional;

public interface BookclubDao {
     BookClub createBookclub(BookClub bookClub);
     void addMember(Long clubId, String username, String role);
    List<BookClubResponse> getBookClubForMember(String username);
    BookClubDetailResponse getClubDetails(Long clubId, String username);
    boolean bookclubExists(Long clubId);
    Optional<String> getBookClubName(Long clubId);
}
