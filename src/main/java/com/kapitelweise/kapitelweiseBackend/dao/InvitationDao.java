package com.kapitelweise.kapitelweiseBackend.dao;

import com.kapitelweise.kapitelweiseBackend.domain.Invitation;

import java.util.Optional;

public interface InvitationDao {
    Optional<Long> getUserId(String username);
    boolean isUserAdmin(Long userId, Long bookClubId);
    Invitation createInvitation(Invitation invitation);
    boolean invitationPending(Long invitedUserId, Long bookClubId);
    boolean isAlreadyMember(Long invitedUserId, Long bookClubId);
    boolean bookclubExists(Long bookClubId);
    void acceptInvitation();

}
