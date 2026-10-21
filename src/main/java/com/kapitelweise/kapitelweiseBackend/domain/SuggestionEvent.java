package com.kapitelweise.kapitelweiseBackend.domain;

public record SuggestionEvent (String type, Long suggestionId, Long bookClubId, String isbn) {
}

