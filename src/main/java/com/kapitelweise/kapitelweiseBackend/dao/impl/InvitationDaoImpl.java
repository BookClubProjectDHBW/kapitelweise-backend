package com.kapitelweise.kapitelweiseBackend.dao.impl;


import com.kapitelweise.kapitelweiseBackend.dao.InvitationDao;
import com.kapitelweise.kapitelweiseBackend.domain.Invitation;
import com.kapitelweise.kapitelweiseBackend.domain.InvitationStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InvitationDaoImpl implements InvitationDao {

    private final JdbcTemplate jdbcTemplate;

    public InvitationDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }



    public Optional<Long> getUserId(String username){
     return jdbcTemplate.queryForList("select id from users where username = ?", Long.class, username)
             .stream().findFirst();

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
    public boolean bookclubExists(Long bookClubId){
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM bookclub WHERE id = ?",
                Integer.class, bookClubId);
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
    public void acceptInvitation() {

    }


}
