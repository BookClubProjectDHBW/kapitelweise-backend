package com.kapitelweise.kapitelweiseBackend.dao;

public interface MemberDao {
    void upsertMember(Long userId, String username, Long bookclubId, String role);
}
