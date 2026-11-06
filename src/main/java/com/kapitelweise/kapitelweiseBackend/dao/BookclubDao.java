package com.kapitelweise.kapitelweiseBackend.dao;


import com.kapitelweise.kapitelweiseBackend.domain.SuggestionEvent;
import com.kapitelweise.kapitelweiseBackend.request.BookRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookClubResponse;
import com.kapitelweise.kapitelweiseBackend.response.BookResponse;
import com.kapitelweise.kapitelweiseBackend.response.SuggestionsResponse;

import java.util.List;


public interface BookclubDao {
    boolean isUserAdmin (String username,Long clubId);
    SuggestionsResponse saveBooksSuggestion(Long clubId, BookRequest request, String username);
    boolean isMember(String username, Long clubId);
    List<SuggestionsResponse> getBookSuggestions(Long clubId, String username);
    boolean checkExistence(Long clubId, Long suggestionId);
    boolean checkStatus(Long clubId, Long suggestionId);
    boolean activeBook(Long clubId);
    BookResponse saveCurrentBook(Long clubId, Long suggestionId);
    BookResponse getCurrentBook(Long clubId);
    void deleteSuggestion(String username, Long clubId);
    void deleteSuggestions(Long clubId);
    void markSuggestionReady(Long suggestionId, String title, String author, Integer pages, Integer firstPublishYear);
    void markSuggestionRejected(Long suggestionId);
    List<SuggestionEvent> getPendingSuggestions();
    //void updateSuggestion(Long suggestionId, String status, String title, String author, Integer pages, Integer firstPublishYear);
    String deleteBook(Long clubId);
    List<BookClubResponse> getBookClubForMember(String username);
}
