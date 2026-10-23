package com.kapitelweise.kapitelweiseBackend.dao;


import com.kapitelweise.kapitelweiseBackend.request.BookRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookResponse;
import com.kapitelweise.kapitelweiseBackend.response.SuggestionsResponse;

import java.util.List;


public interface BookclubDao {
    boolean isUserAdmin (String username,Long clubId);
    SuggestionsResponse saveBooksSuggestion(Long clubId, BookRequest request, String username);
    boolean isMember(String username, Long clubId);
    List<SuggestionsResponse> getBookSuggestions(Long clubId, String username);
    boolean checkExistence(Long clubId, Long proposalId);
    boolean checkStatus(Long clubId, Long proposalId);
    boolean activeBook(Long clubId);
    BookResponse saveCurrentBook(Long clubId, Long proposalId);
    BookResponse getCurrentBook(Long clubId);
    void deleteSuggestion(String username, Long clubId);
    void updateSuggestion(Long suggestionId, String status, String title, String author, Integer pages, Integer firstPublishYear);
}
