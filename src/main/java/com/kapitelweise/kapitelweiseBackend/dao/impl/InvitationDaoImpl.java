package com.kapitelweise.kapitelweiseBackend.dao.impl;


import com.kapitelweise.kapitelweiseBackend.dao.InvitationDao;
import com.kapitelweise.kapitelweiseBackend.domain.Invitation;
import com.kapitelweise.kapitelweiseBackend.domain.InvitationStatus;
import com.kapitelweise.kapitelweiseBackend.response.InvitationFeedbackResponse;
import com.kapitelweise.kapitelweiseBackend.response.InvitationUserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class InvitationDaoImpl implements InvitationDao {

    private final JdbcTemplate jdbcTemplate;

    public InvitationDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public boolean isUserAdmin(Long userId, Long bookClubId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM clubmember WHERE userid = ? AND bookclub_id = ? AND role = 'ADMIN'",
                Integer.class, userId, bookClubId);
        return count != null && count > 0;
    }

    @Override
    public Invitation createInvitation(Invitation invitation) {
        return jdbcTemplate.queryForObject("insert into invitation(invitation_status, invited_user_id, bookclub_id, invited_by_user_id) Values(?,?,?,?) Returning id, invitation_status, created_at",
                (rs, rowNum) -> new Invitation(
                        rs.getLong("id"),
                        InvitationStatus.valueOf(rs.getString("invitation_status")),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ), invitation.getInvitationStatus().name(), invitation.getInvitedUserId(), invitation.getBookClubId(), invitation.getInvitedBy());
    }

    @Override
    public boolean isAlreadyMember(Long invitedUserId, Long bookClubId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM clubmember WHERE userid = ? AND bookclub_id = ?",
                Integer.class, invitedUserId, bookClubId);
        return count != null && count > 0;
    }


    @Override
    public boolean invitationPending(Long invitedUserId, Long bookClubId) {
        Integer count = jdbcTemplate.queryForObject(
        "SELECT COUNT(*) FROM invitation WHERE invited_user_id = ? AND bookclub_id = ? AND invitation_status = 'PENDING'",
                Integer.class, invitedUserId, bookClubId);
        return count != null && count > 0;
    }

    @Override
    public Optional<Invitation> getInvitation(Long invitationId) {
        return jdbcTemplate.query(
                        "SELECT * FROM invitation WHERE id = ?",
                        (rs, rowNum) -> new Invitation(
                                rs.getLong("id"),
                                rs.getLong("bookclub_id"),
                                rs.getLong("invited_user_id"),
                                rs.getLong("invited_by_user_id"),
                                InvitationStatus.valueOf(rs.getString("invitation_status")),
                                rs.getObject("created_at", LocalDateTime.class),
                                rs.getObject("answered_at", LocalDateTime.class)),
                        invitationId)
                .stream()
                .findFirst();
    }

    @Override
    public void changeStatus(Long invitationId, InvitationStatus invitationStatus, LocalDateTime answeredAt) {
        jdbcTemplate.update(
                "UPDATE invitation SET invitation_status = ?, answered_at = ? WHERE id = ?",
                invitationStatus.name(), answeredAt, invitationId);
    }

    @Override
    public void addMember(Long userId, Long bookClubId){
        jdbcTemplate.update("Insert into clubmember (role, userid, bookclub_id) Values(?,?,?)", "MEMBER", userId, bookClubId);
    }

    @Override
    public List<InvitationUserResponse> getUserInvitations(String username) {
        String sql = """
                select invitation.id, invitation.invitation_status, bookclub_id, bookclub.name
                from invitation 
                join users on users.id = invitation.invited_user_id
                join bookclub on bookclub.id = invitation.bookclub_id
                where  users.username = ?;
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new InvitationUserResponse(
                                rs.getLong("id"),
                                rs.getString("invitation_status"),
                                rs.getLong("bookclub_id"),
                                rs.getString("name")

                        ),
                 username
        );


    }
}
