package com.kapitelweise.kapitelweiseBackend.domain;

public record MemberEvent(String type, Long userId, String username, Long bookclubId, String role) {
}
