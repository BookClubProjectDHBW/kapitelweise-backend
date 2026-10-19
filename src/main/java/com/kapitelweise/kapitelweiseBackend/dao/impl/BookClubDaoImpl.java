package com.kapitelweise.kapitelweiseBackend.dao.impl;

import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.request.BookRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookClubDetailResponse;
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
    public boolean bookclubExists(Long clubId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM bookclub WHERE id = ?",
                Integer.class, clubId);
        return count != null && count > 0;
    }

        public BookClubDetailResponse getClubDetails(Long clubId, String username){
        String sql= """
                select bookclub.id, bookclub.name,  bookclub.description, clubmember.role
                from bookclub
                join clubmember on clubmember.bookclub_id = bookclub.id
                join users on users.id = clubmember.userid
                where bookclub.id = ? and users.username = ? ;
                """;
            return jdbcTemplate.queryForObject(sql,(rs, rowNum) ->
                            new BookClubDetailResponse(
                                    rs.getLong("id"),
                                    rs.getString("name"),
                                    rs.getString("description"),
                                    rs.getString("role")
                            ),

                     clubId, username
            );
        }

    @Override
    public boolean isUserAdmin(Long userId, Long bookClubId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM clubmember WHERE userid = ? AND bookclub_id = ? AND role = 'ADMIN'",
                Integer.class, userId, bookClubId);
        return count != null && count > 0;
    }

    @Override
    public boolean isMember(Long userId, Long clubId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM clubmember WHERE userid = ? and bookclub_id = ?",
                Integer.class, userId, clubId);
        return count != null && count > 0;
    }

        public BookRequest saveBooksSuggestion(Long clubId, BookRequest request, String username) {
        return request;
        }

}
