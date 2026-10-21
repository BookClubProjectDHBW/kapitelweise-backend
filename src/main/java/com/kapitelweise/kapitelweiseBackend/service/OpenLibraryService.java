package com.kapitelweise.kapitelweiseBackend.service;

import com.kapitelweise.kapitelweiseBackend.domain.FoundBook;
import com.kapitelweise.kapitelweiseBackend.request.BookRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenLibraryService {

    private final RestClient restClient;

    public OpenLibraryService(
            RestClient.Builder builder,
            @Value("${openLibraryAPI.base-url}")String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public boolean existsInFoundBooks(BookRequest suggestion) {
        if (suggestion == null || suggestion.getIsbn() == null) {
            return false;
        }

        FoundBook foundBook = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/bookSearch")
                        .queryParam("isbn", suggestion.getIsbn())
                        .build())
                .retrieve()
                .body(FoundBook.class);

        if (foundBook == null) {
            return false;
        }

        return sameText(suggestion.getTitle(), foundBook.getTitle())
                && sameText(suggestion.getIsbn(), foundBook.getIsbn())
                && sameNumber(suggestion.getPages(), foundBook.getPages())
                && sameText(suggestion.getAuthor(), foundBook.getAuthor())
                && sameNumber(
                suggestion.getFirstPublishYear(),
                foundBook.getFirstPublishYear()
        );
    }

    private boolean sameText(String first, String second) {
        return first != null && second != null
                && first.trim().equalsIgnoreCase(second.trim());
    }

    private boolean sameNumber(Integer first, Integer second) {
        return first != null && first.equals(second);
    }
}
