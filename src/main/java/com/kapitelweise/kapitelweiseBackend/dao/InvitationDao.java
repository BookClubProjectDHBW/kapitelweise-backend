package com.kapitelweise.kapitelweiseBackend.dao;

import com.kapitelweise.kapitelweiseBackend.domain.Invitation;
import com.kapitelweise.kapitelweiseBackend.domain.InvitationStatus;

import java.time.LocalDateTime;
import java.util.Optional;

public interface InvitationDao {
    boolean isUserAdmin(Long userId, Long bookClubId);
    Invitation createInvitation(Invitation invitation);
    boolean invitationPending(Long invitedUserId, Long bookClubId);
    boolean isAlreadyMember(Long invitedUserId, Long bookClubId);
    Optional<Invitation> getInvitation(Long invitationId);
    void changeStatus(Long invitationId, InvitationStatus invitationStatus, LocalDateTime answeredAt);
    void addMember(Long userId, Long bookClubId);

}
