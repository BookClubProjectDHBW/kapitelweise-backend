package com.kapitelweise.kapitelweiseBackend.dao;


import com.kapitelweise.kapitelweiseBackend.request.BookRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookClubDetailResponse;


public interface BookclubDao {
    BookClubDetailResponse getClubDetails(Long clubId, String username);
    boolean bookclubExists(Long clubId);
    boolean isUserAdmin (Long userId,Long clubId);
    BookRequest saveBooksSuggestion(Long clubId, BookRequest request, String username);
    boolean isMember(Long userId, Long clubId);
}
