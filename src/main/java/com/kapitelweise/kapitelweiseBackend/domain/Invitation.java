package com.kapitelweise.kapitelweiseBackend.domain;

import java.time.LocalDateTime;

public class Invitation {

    private Long id;
    private Long bookclubId;
    private Long invitedUserId;
    private Long invitedByUserId;
    private InvitationStatus invitationStatus;
    private LocalDateTime created_at;
    private LocalDateTime answered_at;   // null, solange nicht beantwortet

    public Invitation(Long id, Long bookclubId, Long invitedUserId, Long invitedByUserId,
                      InvitationStatus invitationStatus, LocalDateTime created_at, LocalDateTime answered_at) {
        this.id = id;
        this.bookclubId = bookclubId;
        this.invitedUserId = invitedUserId;
        this.invitedByUserId = invitedByUserId;
        this.invitationStatus = invitationStatus;
        this.created_at = created_at;
        this.answered_at = answered_at;
    }

    public Invitation(Long bookclubId, Long invitedUserId, Long invitedByUserId, InvitationStatus invitationStatus) {
        this.bookclubId = bookclubId;
        this.invitedUserId = invitedUserId;
        this.invitedByUserId = invitedByUserId;
        this.invitationStatus = invitationStatus;
    }

    public Invitation(Long id, InvitationStatus invitationStatus, LocalDateTime created_at){
        this.id = id;
        this.invitationStatus = invitationStatus;
        this.created_at=created_at;
    }

    public Long getId() { return id; }
    public Long getBookClubId() { return bookclubId; }
    public Long getInvitedUserId() { return invitedUserId; }
    public Long getInvitedBy() { return invitedByUserId; }
    public InvitationStatus getInvitationStatus() { return invitationStatus; }
    public LocalDateTime getCreated_at() { return created_at; }
    public LocalDateTime getAnswered_at() { return answered_at; }
}