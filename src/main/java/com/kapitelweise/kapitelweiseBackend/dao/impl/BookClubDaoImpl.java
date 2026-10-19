package com.kapitelweise.kapitelweiseBackend.dao.impl;

import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.request.BookRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public class BookClubDaoImpl implements BookclubDao {

    private final JdbcTemplate jdbcTemplate;

    public BookClubDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public boolean isUserAdmin(String username, Long bookClubId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM club_member WHERE username = ? AND bookclub_id = ? AND role = 'ADMIN'",
                Integer.class, username, bookClubId);
        return count != null && count > 0;
    }

    @Override
    public boolean isMember(String username, Long clubId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM club_member WHERE username = ? and bookclub_id = ?",
                Integer.class, username, clubId);
        return count != null && count > 0;
    }


    public BookRequest saveBooksSuggestion(Long clubId, BookRequest request, String username) {
        String sql = """
                INSERT INTO club_suggestions
                    (bookclub_id, title, isbn, pages, author, first_publish_year)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                clubId,
                request.getTitle(),
                request.getIsbn(),
                request.getPages(),
                request.getAuthor(),
                request.getFirstPublishYear());
        return request;
    }

}
