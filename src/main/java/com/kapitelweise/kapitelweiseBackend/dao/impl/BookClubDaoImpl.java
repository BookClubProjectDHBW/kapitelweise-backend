package com.kapitelweise.kapitelweiseBackend.dao.impl;

import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.response.BookClubDetailResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;




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
}
