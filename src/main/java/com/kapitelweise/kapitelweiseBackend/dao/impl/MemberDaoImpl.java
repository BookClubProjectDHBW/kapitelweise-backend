package com.kapitelweise.kapitelweiseBackend.dao.impl;

import com.kapitelweise.kapitelweiseBackend.dao.MemberDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class MemberDaoImpl implements MemberDao {


    private final JdbcTemplate jdbcTemplate;

    public MemberDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void upsertMember(Long userId, String username, Long bookclubId, String role) {
        String sql = """
        INSERT INTO club_member (user_id, username, bookclub_id, role)
        VALUES (?, ?, ?, ?)
        ON CONFLICT (user_id, bookclub_id) 
        DO UPDATE SET username = EXCLUDED.username, role = EXCLUDED.role
    """;

        jdbcTemplate.update(sql, userId, username, bookclubId, role);
    }
    @Override
public void removeMember(Long userId, Long bookclubId) {
    jdbcTemplate.update(
            "DELETE FROM club_member WHERE user_id = ? AND bookclub_id = ?",
            userId, bookclubId
    );
}
}
