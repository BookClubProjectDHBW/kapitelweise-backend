package com.kapitelweise.kapitelweiseBackend.dao;

import com.kapitelweise.kapitelweiseBackend.domain.Invitation;


public interface InvitationDao {
    boolean isUserAdmin(Long userId, Long bookClubId);
    Invitation createInvitation(Invitation invitation);
    boolean invitationPending(Long invitedUserId, Long bookClubId);
    boolean isAlreadyMember(Long invitedUserId, Long bookClubId);

}
