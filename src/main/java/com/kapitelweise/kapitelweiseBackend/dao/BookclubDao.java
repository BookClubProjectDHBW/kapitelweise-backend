package com.kapitelweise.kapitelweiseBackend.dao;

import com.kapitelweise.kapitelweiseBackend.domain.BookClub;
import com.kapitelweise.kapitelweiseBackend.request.BookRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookClubDetailResponse;
import com.kapitelweise.kapitelweiseBackend.response.BookClubResponse;

import java.util.List;
import java.util.Optional;

public interface BookclubDao {
    BookClubDetailResponse getClubDetails(Long clubId, String username);
    boolean bookclubExists(Long clubId);
    boolean isUserAdmin (Long userId,Long clubId);
    BookRequest saveBooksSuggestion(Long clubId, BookRequest request);
}
