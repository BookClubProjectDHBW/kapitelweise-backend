package com.kapitelweise.kapitelweiseBackend.dao;


import com.kapitelweise.kapitelweiseBackend.request.BookRequest;


public interface BookclubDao {
    boolean isUserAdmin (String username,Long clubId);
    BookRequest saveBooksSuggestion(Long clubId, BookRequest request, String username);
    boolean isMember(String username, Long clubId);
}
